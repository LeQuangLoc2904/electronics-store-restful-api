package com.loc.electronics_store.service.impl;

import com.loc.electronics_store.dto.request.coupon.CouponCreationRequest;
import com.loc.electronics_store.dto.request.coupon.CouponUpdateRequest;
import com.loc.electronics_store.dto.response.coupon.CouponResponse;
import com.loc.electronics_store.entity.Coupon;
import com.loc.electronics_store.entity.UserCoupon;
import com.loc.electronics_store.exception.AppException;
import com.loc.electronics_store.exception.ErrorCode;
import com.loc.electronics_store.mapper.CouponMapper;
import com.loc.electronics_store.repository.CouponRepository;
import com.loc.electronics_store.service.CouponService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CouponServiceImpl implements CouponService {
    CouponRepository couponRepository;
    CouponMapper couponMapper;

    @Scheduled(initialDelay = 5000, fixedRate = 3600000)
    public void updateCouponStatus() {
        LocalDateTime now = LocalDateTime.now();
        log.info("Bắt đầu quét cập nhật trạng thái Coupon lúc: {}", now);

        int deactivatedCount = couponRepository.deactivateInvalidCoupons(now);

        log.info("Hoàn tất: Đã tắt {} mã", deactivatedCount);
    }

    @Override
    @Transactional
    public CouponResponse createCoupon(CouponCreationRequest request) {
        if (couponRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.COUPON_EXISTED);
        }

        return couponMapper.toResponse(couponRepository.save(couponMapper.toEntity(request)));
    }

    @Override
    @Transactional
    public CouponResponse updateCoupon(Long couponId, CouponUpdateRequest request) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_FOUND));

        coupon.setDiscountType(request.getDiscountType());
        coupon.setValue(request.getValue());
        coupon.setMinOrderValue(request.getMinOrderValue());
        coupon.setMaxDiscountAmount(request.getMaxDiscountAmount());
        coupon.setUsageLimit(request.getUsageLimit());
        coupon.setStartDate(request.getStartDate());
        coupon.setEndDate(request.getEndDate());

        Coupon updatedCoupon = couponRepository.save(coupon);

        return couponMapper.toResponse(updatedCoupon);
    }

    @Override
    @Transactional
    public void deleteCoupon(Long couponId) {
        if (!couponRepository.existsById(couponId)) {
            throw new AppException(ErrorCode.COUPON_NOT_FOUND);
        }

        couponRepository.deleteById(couponId);
        log.info("Coupon deleted successfully with id: {}", couponId);
    }

    @Override
    public List<CouponResponse> getAllValidCoupons() {
        LocalDateTime now = LocalDateTime.now();
        List<Coupon> validCoupons = couponRepository.findByIsActiveTrue();

        return validCoupons.stream()
                .map(couponMapper::toResponse)
                .toList();
    }

    @Override
    public CouponResponse getCouponByCode(String code) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_FOUND));

        return couponMapper.toResponse(coupon);
    }

    @Override
    public void validateCouponForCart(Coupon coupon, Double cartTotal) {
        // Check date range
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(coupon.getStartDate())) {
            throw new AppException(ErrorCode.COUPON_NOT_STARTED);
        }
        if (now.isAfter(coupon.getEndDate())) {
            throw new AppException(ErrorCode.COUPON_EXPIRED);
        }

        // Check usage limit
        if (coupon.getUsedCount() >= coupon.getUsageLimit()) {
            throw new AppException(ErrorCode.COUPON_USAGE_LIMIT_EXCEEDED);
        }

        // Check minimum order value
        if (cartTotal < coupon.getMinOrderValue()) {
            throw new AppException(ErrorCode.COUPON_MINIMUM_ORDER_NOT_MET);
        }
    }

    @Override
    public Double calculateDiscount(List<UserCoupon> userCoupons, Double cartTotal) {
        Double discount = 0.0;

        for (UserCoupon o : userCoupons) {
            Coupon coupon = o.getCoupon();

            if ("FIXED".equals(coupon.getDiscountType())) {
                discount += coupon.getValue();
            }
        }

        if (discount >= cartTotal) discount = cartTotal;

        return discount;
    }
}


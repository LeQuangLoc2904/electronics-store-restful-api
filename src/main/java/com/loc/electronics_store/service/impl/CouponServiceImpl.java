package com.loc.electronics_store.service.impl;

import com.loc.electronics_store.dto.request.coupon.CouponRequest;
import com.loc.electronics_store.dto.response.coupon.CouponResponse;
import com.loc.electronics_store.entity.Coupon;
import com.loc.electronics_store.exception.AppException;
import com.loc.electronics_store.exception.ErrorCode;
import com.loc.electronics_store.mapper.CouponMapper;
import com.loc.electronics_store.repository.CouponRepository;
import com.loc.electronics_store.service.CouponService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    @Transactional
    public CouponResponse createCoupon(CouponRequest request) {
        log.info("Creating new coupon with code: {}", request.getCode());

        if (couponRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.BAD_REQUEST);
        }

        Coupon coupon = couponMapper.toEntity(request);
        Coupon savedCoupon = couponRepository.save(coupon);

        log.info("Coupon created successfully with id: {}", savedCoupon.getId());
        return couponMapper.toResponse(savedCoupon);
    }

    @Override
    @Transactional
    public CouponResponse updateCoupon(Long couponId, CouponRequest request) {
        log.info("Updating coupon with id: {}", couponId);

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_FOUND));

        coupon.setDiscountType(request.getDiscountType());
        coupon.setValue(request.getValue());
        coupon.setMinOrderValue(request.getMinOrderValue());
        coupon.setMaxDiscountAmount(request.getMaxDiscountAmount());
        coupon.setUsageLimit(request.getUsageLimit());
        coupon.setStartDate(request.getStartDate());
        coupon.setEndDate(request.getEndDate());
        coupon.setActive(request.isActive());

        Coupon updatedCoupon = couponRepository.save(coupon);
        log.info("Coupon updated successfully with id: {}", couponId);

        return couponMapper.toResponse(updatedCoupon);
    }

    @Override
    @Transactional
    public void deleteCoupon(Long couponId) {
        log.info("Deleting coupon with id: {}", couponId);

        if (!couponRepository.existsById(couponId)) {
            throw new AppException(ErrorCode.COUPON_NOT_FOUND);
        }

        couponRepository.deleteById(couponId);
        log.info("Coupon deleted successfully with id: {}", couponId);
    }

    @Override
    public List<CouponResponse> getAllValidCoupons() {
        log.info("Fetching all valid coupons");

        LocalDateTime now = LocalDateTime.now();
        List<Coupon> validCoupons = couponRepository.findValidCoupons(now);

        return validCoupons.stream()
                .filter(Coupon::isActive)
                .filter(c -> c.getUsedCount() < c.getUsageLimit())
                .map(couponMapper::toResponse)
                .toList();
    }

    @Override
    public CouponResponse getCouponByCode(String code) {
        log.info("Fetching coupon with code: {}", code);

        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_FOUND));

        return couponMapper.toResponse(coupon);
    }

    @Override
    public void validateCouponForCart(Coupon coupon, Double cartTotal) {
        log.info("Validating coupon: {} for cart total: {}", coupon.getCode(), cartTotal);

        // Check if coupon is active
        if (!coupon.isActive()) {
            throw new AppException(ErrorCode.COUPON_NOT_ACTIVE);
        }

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
    public Double calculateDiscount(Coupon coupon, Double cartTotal) {
        log.info("Calculating discount for coupon: {} with cart total: {}", coupon.getCode(), cartTotal);

        Double discount = 0.0;

        if ("PERCENTAGE".equals(coupon.getDiscountType())) {
            discount = (cartTotal * coupon.getValue()) / 100;
            // Cap at maxDiscountAmount if set
            if (coupon.getMaxDiscountAmount() != null && discount > coupon.getMaxDiscountAmount()) {
                discount = coupon.getMaxDiscountAmount();
            }
        } else if ("FIXED".equals(coupon.getDiscountType())) {
            discount = coupon.getValue();
            // Ensure discount doesn't exceed cart total
            if (discount > cartTotal) {
                discount = cartTotal;
            }
        }

        log.info("Calculated discount: {} for coupon: {}", discount, coupon.getCode());
        return discount;
    }
}


package com.loc.electronics_store.mapper;

import com.loc.electronics_store.dto.request.coupon.CouponCreationRequest;
import com.loc.electronics_store.dto.response.coupon.CouponResponse;
import com.loc.electronics_store.entity.Coupon;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {

    public CouponResponse toResponse(Coupon coupon) {
        if (coupon == null) {
            return null;
        }

        String description = generateDescription(coupon);

        return CouponResponse.builder()
                .id(coupon.getId())
                .code(coupon.getCode())
                .discountType(coupon.getDiscountType())
                .value(coupon.getValue())
                .minOrderValue(coupon.getMinOrderValue())
                .maxDiscountAmount(coupon.getMaxDiscountAmount())
                .usageLimit(coupon.getUsageLimit())
                .usedCount(coupon.getUsedCount())
                .startDate(coupon.getStartDate())
                .endDate(coupon.getEndDate())
                .isActive(coupon.isActive())
                .description(description)
                .build();
    }

    public Coupon toEntity(CouponCreationRequest request) {
        if (request == null) {
            return null;
        }

        return Coupon.builder()
                .code(request.getCode())
                .discountType(request.getDiscountType())
                .value(request.getValue())
                .minOrderValue(request.getMinOrderValue())
                .maxDiscountAmount(request.getMaxDiscountAmount())
                .usageLimit(request.getUsageLimit())
                .usedCount(0)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isActive(true)
                .build();
    }

    private String generateDescription(Coupon coupon) {
        if ("PERCENTAGE".equals(coupon.getDiscountType())) {
            return String.format("Giảm %s%% (tối đa %s)",
                coupon.getValue(),
                coupon.getMaxDiscountAmount() != null ? coupon.getMaxDiscountAmount() : "không giới hạn");
        } else if ("FIXED".equals(coupon.getDiscountType())) {
            return String.format("Giảm %s VND", coupon.getValue());
        }
        return "";
    }
}


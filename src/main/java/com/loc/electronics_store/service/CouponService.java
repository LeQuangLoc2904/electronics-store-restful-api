package com.loc.electronics_store.service;

import com.loc.electronics_store.dto.request.coupon.CouponCreationRequest;
import com.loc.electronics_store.dto.request.coupon.CouponUpdateRequest;
import com.loc.electronics_store.dto.response.coupon.CouponResponse;
import com.loc.electronics_store.entity.Coupon;

import java.util.List;

public interface CouponService {
    // Admin operations
    CouponResponse createCoupon(CouponCreationRequest request);
    CouponResponse updateCoupon(Long couponId, CouponUpdateRequest request);
    void deleteCoupon(Long couponId);

    // User operations - get available coupons
    List<CouponResponse> getAllValidCoupons();
    CouponResponse getCouponByCode(String code);

    // Validation
    void validateCouponForCart(Coupon coupon, Double cartTotal);

    // Discount calculation
    Double calculateDiscount(Coupon coupon, Double cartTotal);
}


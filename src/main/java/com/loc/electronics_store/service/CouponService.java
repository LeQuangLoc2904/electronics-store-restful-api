package com.loc.electronics_store.service;

import com.loc.electronics_store.dto.request.coupon.CouponRequest;
import com.loc.electronics_store.dto.response.coupon.CouponResponse;
import com.loc.electronics_store.entity.Coupon;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    // Admin operations
    CouponResponse createCoupon(CouponRequest request);
    CouponResponse updateCoupon(Long couponId, CouponRequest request);
    void deleteCoupon(Long couponId);

    // User operations - get available coupons
    List<CouponResponse> getAllValidCoupons();
    CouponResponse getCouponByCode(String code);

    // Validation
    void validateCouponForCart(Coupon coupon, Double cartTotal);

    // Discount calculation
    Double calculateDiscount(Coupon coupon, Double cartTotal);
}


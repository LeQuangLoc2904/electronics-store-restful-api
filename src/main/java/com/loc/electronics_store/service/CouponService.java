package com.loc.electronics_store.service;

import com.loc.electronics_store.dto.request.coupon.CouponCreationRequest;
import com.loc.electronics_store.dto.request.coupon.CouponUpdateRequest;
import com.loc.electronics_store.dto.response.coupon.CouponResponse;
import com.loc.electronics_store.entity.Coupon;

import java.util.List;

public interface CouponService {
    CouponResponse createCoupon(CouponCreationRequest request);
    CouponResponse updateCoupon(Long couponId, CouponUpdateRequest request);
    void deleteCoupon(Long couponId);

    List<CouponResponse> getAllValidCoupons();
    CouponResponse getCouponByCode(String code);

    void validateCouponForCart(Coupon coupon, Double cartTotal);

    Double calculateDiscount(Coupon coupon, Double cartTotal);
}


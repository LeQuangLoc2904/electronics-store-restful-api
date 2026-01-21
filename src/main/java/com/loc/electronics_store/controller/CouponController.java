package com.loc.electronics_store.controller;

import com.loc.electronics_store.dto.request.coupon.CouponCreationRequest;
import com.loc.electronics_store.dto.request.coupon.CouponUpdateRequest;
import com.loc.electronics_store.dto.response.ApiResponse;
import com.loc.electronics_store.dto.response.coupon.CouponResponse;
import com.loc.electronics_store.service.CouponService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/coupons")
public class CouponController {
    CouponService couponService;

    /**
     * Admin: Create new coupon
     */
    @PostMapping
    ApiResponse<CouponResponse> createCoupon(@RequestBody CouponCreationRequest request) {
        return ApiResponse.<CouponResponse>builder()
                .result(couponService.createCoupon(request))
                .message("Coupon created successfully")
                .build();
    }

    /**
     * Admin: Update coupon
     */
    @PutMapping("/{couponId}")
    ApiResponse<CouponResponse> updateCoupon(
            @PathVariable Long couponId,
            @RequestBody CouponUpdateRequest request) {
        return ApiResponse.<CouponResponse>builder()
                .result(couponService.updateCoupon(couponId, request))
                .message("Coupon updated successfully")
                .build();
    }

    /**
     * Admin: Delete coupon
     */
    @DeleteMapping("/{couponId}")
    ApiResponse<Void> deleteCoupon(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
        return ApiResponse.<Void>builder()
                .message("Coupon deleted successfully")
                .build();
    }

    /**
     * User: Get all available coupons
     */
    @GetMapping("/available")
    ApiResponse<List<CouponResponse>> getAllValidCoupons() {
        return ApiResponse.<List<CouponResponse>>builder()
                .result(couponService.getAllValidCoupons())
                .message("Retrieved all valid coupons")
                .build();
    }

    /**
     * User: Get coupon by code
     */
    @GetMapping("/code/{code}")
    ApiResponse<CouponResponse> getCouponByCode(@PathVariable String code) {
        return ApiResponse.<CouponResponse>builder()
                .result(couponService.getCouponByCode(code))
                .message("Retrieved coupon details")
                .build();
    }
}


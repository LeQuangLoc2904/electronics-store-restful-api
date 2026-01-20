package com.loc.electronics_store.controller;


import com.loc.electronics_store.dto.request.coupon.ApplyCouponRequest;
import com.loc.electronics_store.dto.request.cart.CartResponse;
import com.loc.electronics_store.dto.response.ApiResponse;
import com.loc.electronics_store.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/cart")
public class CartController {
    CartService cartService;

    @GetMapping
    ApiResponse<CartResponse> getCart(@RequestParam(required = false) Long couponId) {
        return ApiResponse.<CartResponse>builder()
                .result(couponId == null ? cartService.getCart(Optional.empty())
                                        : cartService.getCart(Optional.ofNullable(couponId)))
                .build();
    }

    @PostMapping("/item/add")
    ApiResponse<CartResponse> addProduct(@RequestParam Long productId, @RequestParam(required = false) Long couponId) {
        return ApiResponse.<CartResponse>builder()
                .result(couponId == null ? cartService.addItem(productId,Optional.empty())
                        : cartService.addItem(productId, Optional.ofNullable(couponId)))
                .build();
    }

    @PostMapping("/item/decrease")
    ApiResponse<CartResponse> decreaseQuantity(@RequestParam Long productId, @RequestParam(required = false) Long couponId) {
        return ApiResponse.<CartResponse>builder()
                .result(couponId == null ? cartService.decreaseQuantity(productId, Optional.empty())
                        : cartService.decreaseQuantity(productId, Optional.ofNullable(couponId)))
                .build();
    }

    @DeleteMapping("item/delete")
    ApiResponse<CartResponse> delete(@RequestParam Long productId, @RequestParam(required = false) Long couponId) {
        return ApiResponse.<CartResponse>builder()
                .result(couponId == null ? cartService.deleteItem(productId, Optional.empty())
                        : cartService.deleteItem(productId, Optional.ofNullable(couponId)))
                .build();
    }

    /**
     * Apply coupon to cart
     */
    @PostMapping("/coupon/apply")
    ApiResponse<CartResponse> applyCoupon(@RequestBody ApplyCouponRequest request) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.applyCoupon(request.getCouponCode()))
                .message("Coupon applied successfully")
                .build();
    }

    /**
     * Remove applied coupon from cart
     */
    @DeleteMapping("/coupon/remove")
    ApiResponse<CartResponse> removeCoupon(@RequestParam(required = false) Long couponId) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.removeCoupon(Optional.ofNullable(couponId)))
                .message("Coupon removed successfully")
                .build();
    }
}

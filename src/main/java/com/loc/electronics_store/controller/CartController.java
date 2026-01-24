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
    ApiResponse<CartResponse> getCart() {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.getCart())
                .build();
    }

    @PostMapping("/item/add")
    ApiResponse<CartResponse> addProduct(@RequestParam Long productId) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.addItem(productId))
                .build();
    }

    @PostMapping("/item/decrease")
    ApiResponse<CartResponse> decreaseQuantity(@RequestParam Long productId) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.decreaseQuantity(productId))
                .build();
    }

    @DeleteMapping("item/delete")
    ApiResponse<CartResponse> delete(@RequestParam Long productId) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.deleteItem(productId))
                .build();
    }

    @PostMapping("/coupon/apply")
    ApiResponse<CartResponse> applyCoupon(@RequestBody ApplyCouponRequest request) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.applyCoupon(request.getCouponCode()))
                .message("Coupon applied successfully")
                .build();
    }

    @DeleteMapping("/coupon/remove")
    ApiResponse<CartResponse> removeCoupon(@RequestParam(required = false) Long couponId) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.removeCoupon(couponId))
                .message("Coupon removed successfully")
                .build();
    }
}

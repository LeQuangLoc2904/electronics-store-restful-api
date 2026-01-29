package com.loc.electronics_store.controller;


import com.loc.electronics_store.dto.request.coupon.CouponCreationRequest;
import com.loc.electronics_store.dto.response.ApiResponse;
import com.loc.electronics_store.dto.response.coupon.CouponResponse;
import com.loc.electronics_store.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/orders")
public class OrderController {
    OrderService orderService;

    @PostMapping
    ApiResponse<String> createOrder(@RequestParam String paymentMethod) {
        return ApiResponse.<String>builder()
                .result(orderService.createOrder(paymentMethod))
                .build();
    }
}

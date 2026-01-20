package com.loc.electronics_store.dto.request.cart;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.loc.electronics_store.dto.response.coupon.CouponResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CartResponse {
    List<CartItemResponse> cartItemResponses;
    Double subTotal; // Tổng tiền trước khi áp dụng coupon
    Double discountAmount; // Số tiền được giảm
    Double totalPrice; // Tổng tiền sau khi áp dụng coupon
    CouponResponse appliedCoupon; // Coupon được áp dụng (nếu có)
}

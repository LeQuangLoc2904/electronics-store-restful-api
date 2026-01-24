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
//@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CartResponse {
    List<CartItemResponse> cartItemResponses;
    Double subTotal;
    Double discountAmount;
    Double totalPrice;
    List<CouponResponse> appliedCoupons;
}

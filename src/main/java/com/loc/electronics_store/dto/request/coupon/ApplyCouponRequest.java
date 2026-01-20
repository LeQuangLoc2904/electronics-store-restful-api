package com.loc.electronics_store.dto.request.coupon;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplyCouponRequest {
    String couponCode;
}


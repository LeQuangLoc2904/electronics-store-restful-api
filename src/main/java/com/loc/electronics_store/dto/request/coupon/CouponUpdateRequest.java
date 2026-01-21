package com.loc.electronics_store.dto.request.coupon;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponUpdateRequest {
    String code;
    String discountType; // PERCENTAGE, FIXED
    Double value; // 20 (for 20%) or 50000 (for 50k VND)
    Double minOrderValue;
    Double maxDiscountAmount;
    Integer usageLimit;
    LocalDateTime startDate;
    LocalDateTime endDate;
}


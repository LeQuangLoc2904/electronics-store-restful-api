package com.loc.electronics_store.dto.response.coupon;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponResponse {
    Long id;
    String code;
    String discountType;
    Double value;
    Double minOrderValue;
    Double maxDiscountAmount;
    Integer usageLimit;
    Integer usedCount;
    LocalDateTime startDate;
    LocalDateTime endDate;
    boolean isActive;
    String description; // Helper field to describe the coupon
}


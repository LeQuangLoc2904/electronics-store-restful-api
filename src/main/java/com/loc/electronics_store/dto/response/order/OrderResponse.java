package com.loc.electronics_store.dto.response.order;


import com.loc.electronics_store.dto.response.orderdetail.OrderDetailResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;
    Double totalAmount;
    Double discountAmount;
    Double finalAmount;
    String status;
    String shippingAddress;
    LocalDateTime orderDate;
    List<OrderDetailResponse> orderDetailResponses;
}

package com.loc.electronics_store.dto.response.orderdetail;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    Long id;
    String productName;
    String productImage;
    Integer quantity;
    Double price;
}

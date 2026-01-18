package com.loc.electronics_store.dto.request.cart;


import com.loc.electronics_store.dto.response.product.ProductResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    ProductResponse product;
    Integer quantity;
}

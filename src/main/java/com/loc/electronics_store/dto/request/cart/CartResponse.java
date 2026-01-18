package com.loc.electronics_store.dto.request.cart;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    List<CartItemResponse> cartItemResponses;
    Double totalPrice;
}

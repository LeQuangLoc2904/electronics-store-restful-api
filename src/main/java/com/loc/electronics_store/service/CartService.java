package com.loc.electronics_store.service;

import com.loc.electronics_store.dto.request.cart.CartItemResponse;
import com.loc.electronics_store.dto.request.cart.CartResponse;
import com.loc.electronics_store.entity.CartItem;

import java.util.List;

public interface CartService {
    CartResponse getCart();
    CartResponse addItem(Long productId);
    CartResponse decreaseQuantity(Long id);
    CartResponse deleteItem(Long productId);
    Double getTotalPrice(List<CartItem> cartItems);
}

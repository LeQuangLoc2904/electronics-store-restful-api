package com.loc.electronics_store.service;

import com.loc.electronics_store.dto.request.cart.CartItemResponse;
import com.loc.electronics_store.dto.request.cart.CartResponse;
import com.loc.electronics_store.entity.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartService {
    CartResponse getCart();
    CartResponse addItem(Long productId);
    CartResponse decreaseQuantity(Long id);
    CartResponse deleteItem(Long productId);

    CartResponse applyCoupon(String couponCode);
    CartResponse removeCoupon(Long couponId);

    Double getSubTotalPrice(List<CartItem> cartItems);
}

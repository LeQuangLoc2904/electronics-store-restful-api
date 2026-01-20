package com.loc.electronics_store.service;

import com.loc.electronics_store.dto.request.cart.CartItemResponse;
import com.loc.electronics_store.dto.request.cart.CartResponse;
import com.loc.electronics_store.entity.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartService {
    CartResponse getCart(Optional<Long> couponId);
    CartResponse addItem(Long productId, Optional<Long> couponId);
    CartResponse decreaseQuantity(Long id, Optional<Long> couponId);
    CartResponse deleteItem(Long productId, Optional<Long> couponId);

    // Coupon operations
    CartResponse applyCoupon(String couponCode);
    CartResponse removeCoupon(Optional<Long> couponId);

    // Price calculation methods
    Double getSubTotalPrice(List<CartItem> cartItems);
}

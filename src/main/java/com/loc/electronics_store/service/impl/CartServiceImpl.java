package com.loc.electronics_store.service.impl;


import com.loc.electronics_store.dto.request.cart.CartItemResponse;
import com.loc.electronics_store.dto.request.cart.CartResponse;
import com.loc.electronics_store.entity.CartItem;
import com.loc.electronics_store.entity.Product;
import com.loc.electronics_store.exception.AppException;
import com.loc.electronics_store.exception.ErrorCode;
import com.loc.electronics_store.mapper.ProductMapper;
import com.loc.electronics_store.repository.CartItemRepository;
import com.loc.electronics_store.repository.ProductRepository;
import com.loc.electronics_store.repository.UserRepository;
import com.loc.electronics_store.service.CartService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CartServiceImpl implements CartService {
    CartItemRepository cartItemRepository;
    ProductMapper productMapper;
    UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public CartResponse getCart() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();

        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            return CartResponse.builder()
                    .cartItemResponses(new ArrayList<>())
                    .build();
        }

        var cartItemResponses = cartItems.stream().map(
                cartItem -> CartItemResponse.builder()
                        .product(productMapper.toResponse(cartItem.getProduct()))
                        .quantity(cartItem.getQuantity())
                        .build()
        ).toList();

        return CartResponse.builder()
                .cartItemResponses(cartItemResponses)
                .totalPrice(getTotalPrice(cartItems))
                .build();
    }

    @Override
    public CartResponse addItem(Long productId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();

        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        Product product = productRepository.findByIdAndDeletedFalse(productId);
        CartItem cartItem = cartItemRepository.findByUser_IdAndProduct_Id(user.getId(), productId).orElse(null);

        if (cartItem == null) {
            CartItem newCartItem = CartItem.builder()
                    .product(product)
                    .user(user)
                    .quantity(1)
                    .build();

            cartItemRepository.save(newCartItem);
        }
        else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemRepository.save(cartItem);
        }

        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        var cartItemResponses = cartItems.stream().map(
                item -> CartItemResponse.builder()
                        .product(productMapper.toResponse(item.getProduct()))
                        .quantity(item.getQuantity())
                        .build()
        ).toList();

        return CartResponse.builder()
                .cartItemResponses(cartItemResponses)
                .totalPrice(getTotalPrice(cartItems))
                .build();
    }

    @Override
    public CartResponse decreaseQuantity(Long id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();

        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        Product product = productRepository.findByIdAndDeletedFalse(id);
        CartItem cartItem = cartItemRepository.findByUser_IdAndProduct_Id(user.getId(), id).orElse(null);

        cartItem.setQuantity(cartItem.getQuantity() - 1);
        cartItemRepository.save(cartItem);

        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        var cartItemResponses = cartItems.stream().map(
                item -> CartItemResponse.builder()
                        .product(productMapper.toResponse(item.getProduct()))
                        .quantity(item.getQuantity())
                        .build()
        ).toList();

        return CartResponse.builder()
                .cartItemResponses(cartItemResponses)
                .totalPrice(getTotalPrice(cartItems))
                .build();
    }

    @Override
    @Transactional
    public CartResponse deleteItem(Long productId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();

        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        cartItemRepository.deleteByUserAndProduct_Id(user, productId);

        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        var cartItemResponses = cartItems.stream().map(
                item -> CartItemResponse.builder()
                        .product(productMapper.toResponse(item.getProduct()))
                        .quantity(item.getQuantity())
                        .build()
        ).toList();

        return CartResponse.builder()
                .cartItemResponses(cartItemResponses)
                .totalPrice(getTotalPrice(cartItems))
                .build();
    }

    @Override
    public Double getTotalPrice(List<CartItem> cartItems) {
        Double totalPrice = 0.0;

        for (CartItem item : cartItems) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }

        return totalPrice;
    }
}

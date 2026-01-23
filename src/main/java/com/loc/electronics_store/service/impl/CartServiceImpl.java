package com.loc.electronics_store.service.impl;


import com.loc.electronics_store.dto.request.cart.CartItemResponse;
import com.loc.electronics_store.dto.request.cart.CartResponse;
import com.loc.electronics_store.entity.CartItem;
import com.loc.electronics_store.entity.Coupon;
import com.loc.electronics_store.entity.Product;
import com.loc.electronics_store.entity.UserCoupon;
import com.loc.electronics_store.exception.AppException;
import com.loc.electronics_store.exception.ErrorCode;
import com.loc.electronics_store.mapper.CouponMapper;
import com.loc.electronics_store.mapper.ProductMapper;
import com.loc.electronics_store.repository.CartItemRepository;
import com.loc.electronics_store.repository.CouponRepository;
import com.loc.electronics_store.repository.ProductRepository;
import com.loc.electronics_store.repository.UserCouponRepository;
import com.loc.electronics_store.repository.UserRepository;
import com.loc.electronics_store.service.CartService;
import com.loc.electronics_store.service.CouponService;
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
    ProductRepository productRepository;
    CouponService couponService;
    CouponRepository couponRepository;
    UserCouponRepository userCouponRepository;
    CouponMapper couponMapper;

    @Override
    public CartResponse getCart(Optional<Long> couponId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();

        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            if (couponId.isPresent()) {
                Coupon coupon = couponRepository.findById(couponId.get())
                        .orElseThrow(()-> new AppException(ErrorCode.COUPON_NOT_FOUND));

                coupon.setUsedCount(coupon.getUsedCount() - 1);
                couponRepository.save(coupon);

                Optional<UserCoupon> userCoupon = userCouponRepository
                        .findByUser_IdAndCoupon_IdAndOrderIdIsNull(user.getId(), couponId.get());

                userCouponRepository.delete(userCoupon.get());
            }

            return CartResponse.builder()
                    .cartItemResponses(new ArrayList<>())
                    .subTotal(0.0)
                    .discountAmount(0.0)
                    .totalPrice(0.0)
                    .appliedCoupon(null)
                    .build();
        }

        var cartItemResponses = cartItems.stream().map(
                cartItem -> CartItemResponse.builder()
                        .product(productMapper.toResponse(cartItem.getProduct()))
                        .quantity(cartItem.getQuantity())
                        .build()
        ).toList();

        Double subTotal = getSubTotalPrice(cartItems);
        Double discountAmount = 0.0;
        UserCoupon userCoupon = null;

        if (couponId.isPresent()) {
            Optional<UserCoupon> appliedCoupon = userCouponRepository.findActiveCouponByUserId(user.getId(), couponId.get());
            if (appliedCoupon.isPresent()) {
                userCoupon = appliedCoupon.get();
                Coupon coupon = userCoupon.getCoupon();
                discountAmount = couponService.calculateDiscount(coupon, subTotal);
            }
        }

        Double totalPrice = Math.max(subTotal - discountAmount, 0.0);

        return CartResponse.builder()
                .cartItemResponses(cartItemResponses)
                .subTotal(subTotal)
                .discountAmount(discountAmount)
                .totalPrice(totalPrice)
                .appliedCoupon(userCoupon != null ? couponMapper.toResponse(userCoupon.getCoupon()) : null)
                .build();
    }

    @Override
    public CartResponse addItem(Long productId, Optional<Long> couponId) {
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

        return couponId.isPresent() ? getCart(couponId) : getCart(Optional.empty());
    }

    @Override
    public CartResponse decreaseQuantity(Long id, Optional<Long> couponId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();

        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        Product product = productRepository.findByIdAndDeletedFalse(id);
        CartItem cartItem = cartItemRepository.findByUser_IdAndProduct_Id(user.getId(), id).orElse(null);

        cartItem.setQuantity(cartItem.getQuantity() - 1);
        cartItemRepository.save(cartItem);

        return couponId.isPresent() ? getCart(couponId) : getCart(Optional.empty());
    }

    @Override
    @Transactional
    public CartResponse deleteItem(Long productId, Optional<Long> couponId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();

        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        cartItemRepository.deleteByUserAndProduct_Id(user, productId);

        return couponId.isPresent() ? getCart(couponId) : getCart(Optional.empty());
    }

    @Override
    @Transactional
    public CartResponse applyCoupon(String couponCode) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();

        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        Coupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_FOUND));

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        Double cartTotal = getSubTotalPrice(cartItems);

        couponService.validateCouponForCart(coupon, cartTotal);

        Optional<UserCoupon> existingCoupon = userCouponRepository.findActiveCouponByUserId(user.getId(), Optional.ofNullable(coupon).get().getId());
        if (existingCoupon.isPresent()) {
            throw new AppException(ErrorCode.COUPON_ALREADY_SELECTED);
        }

        UserCoupon userCoupon = UserCoupon.builder()
                .user(user)
                .coupon(coupon)
                .build();
        userCouponRepository.save(userCoupon);

        coupon.setUsedCount(coupon.getUsedCount() + 1);
        if (coupon.getUsedCount() >= coupon.getUsageLimit()) {
            coupon.setActive(false);
        }

        couponRepository.save(coupon);

        return getCart(Optional.ofNullable(coupon.getId()));
    }

    @Override
    @Transactional
    public CartResponse removeCoupon(Optional<Long> couponId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();

        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        Optional<UserCoupon> userCoupon = userCouponRepository.findActiveCouponByUserId(user.getId(), couponId.get());
        if (userCoupon.isPresent()) {
            Coupon coupon = userCoupon.get().getCoupon();
            coupon.setUsedCount(coupon.getUsedCount() - 1);
            couponRepository.save(coupon);

            userCouponRepository.delete(userCoupon.get());
            log.info("Coupon removed successfully for user: {}", username);
        }

        return getCart(Optional.empty());
    }

    @Override
    public Double getSubTotalPrice(List<CartItem> cartItems) {
        Double subTotal = 0.0;

        for (CartItem item : cartItems) {
            subTotal += item.getProduct().getPrice() * item.getQuantity();
        }

        return subTotal;
    }
}

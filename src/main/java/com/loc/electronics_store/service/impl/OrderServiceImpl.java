package com.loc.electronics_store.service.impl;

import com.loc.electronics_store.entity.*;
import com.loc.electronics_store.exception.AppException;
import com.loc.electronics_store.exception.ErrorCode;
import com.loc.electronics_store.repository.CartItemRepository;
import com.loc.electronics_store.repository.OrderRepository;
import com.loc.electronics_store.repository.UserCouponRepository;
import com.loc.electronics_store.repository.UserRepository;
import com.loc.electronics_store.service.CartService;
import com.loc.electronics_store.service.CouponService;
import com.loc.electronics_store.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderServiceImpl implements OrderService {
    UserCouponRepository userCouponRepository;
    CartItemRepository cartItemRepository;
    UserRepository userRepository;
    OrderRepository orderRepository;
    CartService cartService;
    CouponService couponService;


    @Override
    public String createOrder(String paymentMethod) {
        Order order = new Order();

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<UserCoupon> userCoupons = userCouponRepository.findByUser_IdAndOrderIdNull(user.getId());
        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        Double totalAmount = cartService.getSubTotalPrice(cartItems);
        Double discountAmount = couponService.calculateDiscount(userCoupons, totalAmount);
        Double finalAmount = totalAmount - discountAmount;

        List<Coupon> coupons = userCoupons.stream().map(UserCoupon::getCoupon).toList();
        List<OrderDetail> orderDetails = cartItems.stream().map(
                cartItem -> {
                    return OrderDetail.builder()
                            .order(order)
                            .product(cartItem.getProduct())
                            .price(cartItem.getProduct().getPrice())
                            .quantity(cartItem.getQuantity())
                            .build();
                }
        ).toList();

        order.setUser(user);
        order.setCoupons(coupons);
        order.setTotalMoney(totalAmount);
        order.setDiscountAmount(discountAmount);
        order.setFinalAmount(finalAmount);
        order.setStatus("PENDING");
        order.setShippingAddress(user.getAddress());
        order.setPaymentMethod(paymentMethod);
        order.setOrderDetails(orderDetails);
        order.setCreatedAt(LocalDateTime.now());

        orderRepository.save(order);

        userCoupons.forEach(
                userCoupon -> {
                    userCoupon.setOrderId(order.getId());
                    userCouponRepository.save(userCoupon);
                }
        );

        return "Place an order successfully";
    }
}

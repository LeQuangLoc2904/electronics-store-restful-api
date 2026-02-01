package com.loc.electronics_store.service;

import com.loc.electronics_store.dto.response.order.OrderResponse;

import java.util.List;

public interface OrderService {
    String createOrder(String paymentMethod);
    List<OrderResponse> getAll();
    List<OrderResponse> getAllByStatus(String status);
}

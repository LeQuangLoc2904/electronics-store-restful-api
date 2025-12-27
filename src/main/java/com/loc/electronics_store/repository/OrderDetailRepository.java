package com.loc.electronics_store.repository;

import com.loc.electronics_store.entity.OrderDetail;
import com.loc.electronics_store.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrder(Order order);
}


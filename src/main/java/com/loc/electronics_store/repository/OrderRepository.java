package com.loc.electronics_store.repository;

import com.loc.electronics_store.entity.Order;
import com.loc.electronics_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_Username(String username);

    List<Order> findByStatus(String status);
}


package com.loc.electronics_store.repository;

import com.loc.electronics_store.entity.CartItem;
import com.loc.electronics_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);

    void deleteByUserAndProduct_Id(User user, Long productId);
}


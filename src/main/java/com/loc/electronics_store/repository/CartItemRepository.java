package com.loc.electronics_store.repository;

import com.loc.electronics_store.entity.CartItem;
import com.loc.electronics_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    Optional<CartItem> findByUser_IdAndProduct_Id(Long userId, Long productId);
    void deleteByUserAndProduct_Id(User user, Long productId);
}


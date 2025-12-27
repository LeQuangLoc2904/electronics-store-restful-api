package com.loc.electronics_store.repository;

import com.loc.electronics_store.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct_Id(Long productId);

    List<Review> findByUser_Id(Long userId);
}


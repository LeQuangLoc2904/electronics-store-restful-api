package com.loc.electronics_store.repository;

import com.loc.electronics_store.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByIsActiveTrueAndStartDateBeforeAndEndDateAfter(LocalDateTime now1, LocalDateTime now2);

    List<Promotion> findByProducts_Id(Long productId);

    List<Promotion> findByCategories_Id(Long categoryId);
}


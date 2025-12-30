package com.loc.electronics_store.repository;

import com.loc.electronics_store.entity.ProductImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProduct_Id(Long productId);
    @Transactional
    void deleteByProduct_Id(Long productId);
}


package com.loc.electronics_store.repository;

import com.loc.electronics_store.entity.ProductAttribute;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    List<ProductAttribute> findByProduct_Id(Long productId);
    @Transactional
    void deleteByProduct_Id(Long productId);
}


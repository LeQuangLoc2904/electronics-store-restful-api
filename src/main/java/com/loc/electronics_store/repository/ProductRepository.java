package com.loc.electronics_store.repository;

import com.loc.electronics_store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByDeletedFalse();

    Product findByIdAndDeletedFalse(Long id);

    Page<Product> findAllByDeletedFalse(Pageable pageable);

    List<Product> findByNameContainingIgnoreCaseAndDeletedFalse(String name);

    Page<Product> findByCategoryIdAndDeletedFalse(Long categoryId, Pageable pageable);

    boolean existsByNameAndDeletedFalse(String name);
}


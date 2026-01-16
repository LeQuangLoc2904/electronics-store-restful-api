package com.loc.electronics_store.repository;

import com.loc.electronics_store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * JpaSpecificationExecutor cho phép sử dụng Specification pattern
 * Cung cấp các phương thức như findAll(Specification, Pageable)
 */
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findAllByDeletedFalse();

    Product findByIdAndDeletedFalse(Long id);

    Page<Product> findAllByDeletedFalse(Pageable pageable);

    List<Product> findByNameContainingIgnoreCaseAndDeletedFalse(String name);

    Page<Product> findByCategoryIdAndDeletedFalse(Long categoryId, Pageable pageable);

    boolean existsByNameAndDeletedFalse(String name);
}


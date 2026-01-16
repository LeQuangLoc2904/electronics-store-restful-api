package com.loc.electronics_store.repository;

import com.loc.electronics_store.entity.ProductAttribute;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    List<ProductAttribute> findByProduct_Id(Long productId);

    @Transactional
    void deleteByProduct_Id(Long productId);

    /**
     * Lấy tất cả các thuộc tính của sản phẩm trong một category
     * Dùng để lấy danh sách giá trị filter
     */
    @Query("SELECT pa FROM ProductAttribute pa " +
           "WHERE pa.product.category.id = :categoryId " +
           "AND pa.product.deleted = false")
    List<ProductAttribute> findByProduct_Category_Id(@Param("categoryId") Long categoryId);
}


package com.loc.electronics_store.specification;

import com.loc.electronics_store.dto.request.product.ProductFilterRequest;
import com.loc.electronics_store.entity.Product;
import com.loc.electronics_store.entity.ProductAttribute;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * ✨ KEY CONCEPT: SPECIFICATION PATTERN
 *
 * Đây là một design pattern cho phép xây dựng các tiêu chí tìm kiếm một cách linh hoạt
 * mà không cần viết SQL trực tiếp. Spring Data JPA sẽ dịch nó thành SQL.
 *
 * Lợi ích:
 * 1. Tái sử dụng các tiêu chí
 * 2. Kết hợp nhiều tiêu chí một cách dễ dàng
 * 3. An toàn trước SQL Injection
 * 4. Code rõ ràng, dễ maintain
 */
@Component
public class ProductSpecification {

    /**
     * Tạo Specification từ filter request
     * Kết hợp tất cả các tiêu chí thành một Specification duy nhất
     */
    public Specification<Product> filterProducts(ProductFilterRequest filterRequest) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Lọc theo danh mục
            if (filterRequest.getCategoryId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), filterRequest.getCategoryId()));
            }

            // Lọc theo thương hiệu
            if (filterRequest.getBrandIds() != null && !filterRequest.getBrandIds().isEmpty()) {
                predicates.add(root.get("brand").get("id").in(filterRequest.getBrandIds()));
            }

            // Lọc theo giá
            if (filterRequest.getMinPrice() != null && filterRequest.getMinPrice() > 0) {
                predicates.add(criteriaBuilder.ge(root.get("price"), filterRequest.getMinPrice()));
            }
            if (filterRequest.getMaxPrice() != null && filterRequest.getMaxPrice() > 0) {
                predicates.add(criteriaBuilder.le(root.get("price"), filterRequest.getMaxPrice()));
            }

            // Tìm kiếm theo từ khóa
            if (filterRequest.getSearchKeyword() != null && !filterRequest.getSearchKeyword().trim().isEmpty()) {
                String searchPattern = "%" + filterRequest.getSearchKeyword().toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchPattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchPattern)
                ));
            }

            // Lọc theo thuộc tính
            if (filterRequest.getAttributes() != null && !filterRequest.getAttributes().isEmpty()) {
                for (Map.Entry<String, List<String>> entry : filterRequest.getAttributes().entrySet()) {
                    String attributeName = entry.getKey();
                    List<String> attributeValues = entry.getValue();

                    if (attributeValues != null && !attributeValues.isEmpty()) {
                        Subquery<ProductAttribute> subquery = query.subquery(ProductAttribute.class);
                        Root<ProductAttribute> attributeRoot = subquery.from(ProductAttribute.class);

                        subquery.select(attributeRoot)
                            .where(
                                criteriaBuilder.and(
                                    criteriaBuilder.equal(attributeRoot.get("product"), root),
                                    criteriaBuilder.equal(attributeRoot.get("name"), attributeName),
                                    attributeRoot.get("value").in(attributeValues)
                                )
                            );

                        predicates.add(criteriaBuilder.exists(subquery));
                    }
                }
            }

            // Lọc ra các sản phẩm chưa bị xóa
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));

            return predicates.isEmpty()
                ? null
                : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Phương thức helper: Tạo Specification từ các đối số riêng lẻ
     * Thường dùng khi client muốn truyền từng filter một
     */
    public Specification<Product> filterByMultipleCriteria(
            Long categoryId,
            List<Long> brandIds,
            Double minPrice,
            Double maxPrice,
            String keyword,
            Map<String, List<String>> attributes) {

        ProductFilterRequest filterRequest = ProductFilterRequest.builder()
                .categoryId(categoryId)
                .brandIds(brandIds)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .searchKeyword(keyword)
                .attributes(attributes)
                .build();

        return filterProducts(filterRequest);
    }

    /**
     * Lọc theo danh mục
     */
    public Specification<Product> byCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }

    /**
     * Lọc theo nhiều thương hiệu
     */
    public Specification<Product> byBrandIds(List<Long> brandIds) {
        return (root, query, criteriaBuilder) -> {
            if (brandIds == null || brandIds.isEmpty()) {
                return null;
            }
            return root.get("brand").get("id").in(brandIds);
        };
    }

    /**
     * Lọc theo khoảng giá
     */
    public Specification<Product> byPriceRange(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (minPrice != null && minPrice > 0) {
                predicates.add(criteriaBuilder.ge(root.get("price"), minPrice));
            }

            if (maxPrice != null && maxPrice > 0) {
                predicates.add(criteriaBuilder.le(root.get("price"), maxPrice));
            }

            return predicates.isEmpty()
                ? null
                : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Tìm kiếm theo từ khóa
     */
    public Specification<Product> bySearchKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return null;
            }

            String searchPattern = "%" + keyword.toLowerCase() + "%";

            return criteriaBuilder.or(
                criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    searchPattern
                ),
                criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("description")),
                    searchPattern
                )
            );
        };
    }

    /**
     * Lọc theo thuộc tính động
     */
    public Specification<Product> byProductAttributes(Map<String, List<String>> attributes) {
        return (root, query, criteriaBuilder) -> {
            if (attributes == null || attributes.isEmpty()) {
                return null;
            }

            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, List<String>> entry : attributes.entrySet()) {
                String attributeName = entry.getKey();
                List<String> attributeValues = entry.getValue();

                if (attributeValues == null || attributeValues.isEmpty()) {
                    continue;
                }

                Subquery<ProductAttribute> subquery = query.subquery(ProductAttribute.class);
                Root<ProductAttribute> attributeRoot = subquery.from(ProductAttribute.class);

                subquery.select(attributeRoot)
                    .where(
                        criteriaBuilder.and(
                            criteriaBuilder.equal(attributeRoot.get("product"), root),
                            criteriaBuilder.equal(attributeRoot.get("name"), attributeName),
                            attributeRoot.get("value").in(attributeValues)
                        )
                    );

                predicates.add(criteriaBuilder.exists(subquery));
            }

            return predicates.isEmpty()
                ? null
                : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Lọc ra các sản phẩm chưa bị xóa
     */
    public Specification<Product> notDeleted() {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("deleted"), false);
    }
}


package com.loc.electronics_store.dto.request.product;

import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

/**
 * DTO dùng cho cập nhật sản phẩm. Các trường đều tùy chọn (nullable) để hỗ trợ cập nhật từng phần.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {
    private String name;

    private Double price;

    private Integer stockQuantity;

    private String thumbnail;

    private String description;

    private Long categoryId;

    private Long brandId;

    private List<String> imageUrls;

    @Valid
    private List<AttributeRequest> attributes;
}


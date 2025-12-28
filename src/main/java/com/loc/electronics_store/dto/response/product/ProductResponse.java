package com.loc.electronics_store.dto.response.product;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private Double price;
    private Integer stockQuantity;
    private String thumbnail;
    private String description;

    // Trả về thông tin cơ bản của Category/Brand
    private String categoryName;
    private String brandName;

    // Danh sách ảnh
    private List<String> images;

    // Danh sách các thuộc tính chi tiết
    private List<AttributeResponse> attributes;

    // Các trường từ BaseEntity (nếu cần hiển thị ngày tạo)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


package com.loc.electronics_store.dto.request.product;

import jakarta.validation.Valid; // QUAN TRỌNG
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreationRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 0, message = "Giá sản phẩm phải lớn hơn hoặc bằng 0")
    private Double price;

    @NotNull(message = "Số lượng tồn kho không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho không được nhỏ hơn 0")
    private Integer stockQuantity;

    private String thumbnail;

    private String description;

    @NotNull(message = "Category ID không được để trống")
    private Long categoryId;

    @NotNull(message = "Brand ID không được để trống")
    private Long brandId;

    @NotNull(message = "Số lượng ảnh phải từ 1 trở lên")
    private List<String> imageUrls;

    // Phải có @Valid để Spring validate từng AttributeRequest bên trong
    @Valid
    private List<AttributeRequest> attributes;
}


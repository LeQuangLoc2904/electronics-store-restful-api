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
    @NotBlank(message = "PRODUCTNAME_NOTEMPTY")
    private String name;

    @NotNull(message = "PRODUCTPRICE_NOTEMPTY")
    @Min(value = 1, message = "PRODUCTPRICE_INVALID")
    private Double price;

    @NotNull(message = "STOCKQUANTITY_NOTEMPTY")
    @Min(value = 1, message = "STOCKQUANTITY_INVALID")
    private Integer stockQuantity;

    @NotBlank(message = "THUMBNAIL_NOTEMPTY")
    private String thumbnail;

    @NotBlank(message = "DESCRIPTION_NOTEMPTY")
    private String description;

    private Long categoryId;

    private Long brandId;

    private List<String> imageUrls;

    // Phải có @Valid để Spring validate từng AttributeRequest bên trong
    @Valid
    private List<AttributeRequest> attributes;
}


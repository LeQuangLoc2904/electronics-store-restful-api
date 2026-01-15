package com.loc.electronics_store.dto.response.category;


import com.loc.electronics_store.dto.response.brand.BrandResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CategoryResponse {
    Long id;
    String name;
    String slug;
    String description;

    List<BrandResponse> brands;
}

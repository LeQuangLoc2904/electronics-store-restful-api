package com.loc.electronics_store.dto.request.product;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductSearchingKeyword {
    String keyword;
    Integer page;
    Integer size;
    String sortBy;
    String sortDirection;

    public Integer getPageOrDefault() {
        return page != null ? page : 0;
    }

    public Integer getSizeOrDefault() {
        return size != null && size > 0 ? size : 20;
    }

    public String getSortByOrDefault() {
        return sortBy != null ? sortBy : "id";
    }

    public String getSortDirectionOrDefault() {
        return sortDirection != null ? sortDirection : "DESC";
    }
}

package com.loc.electronics_store.mapper;

import com.loc.electronics_store.dto.request.category.CategoryCreationRequest;
import com.loc.electronics_store.dto.request.category.CategoryUpdateRequest;
import com.loc.electronics_store.dto.response.category.CategoryResponse;
import com.loc.electronics_store.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryCreationRequest request);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "brands", target = "brands")
    CategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "brands", ignore = true)
    void updateCategory(CategoryUpdateRequest request, @MappingTarget Category category);
}

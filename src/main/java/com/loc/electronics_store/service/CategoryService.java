package com.loc.electronics_store.service;

import com.loc.electronics_store.dto.request.category.CategoryCreationRequest;
import com.loc.electronics_store.dto.request.category.CategoryUpdateRequest;
import com.loc.electronics_store.dto.response.category.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryResponse> getAll(Pageable pageable);
    CategoryResponse create(CategoryCreationRequest request);
    CategoryResponse update(Long id, CategoryUpdateRequest request);
}

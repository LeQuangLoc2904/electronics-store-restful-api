package com.loc.electronics_store.controller;


import com.loc.electronics_store.dto.request.brand.BrandCreationRequest;
import com.loc.electronics_store.dto.request.brand.BrandUpdateRequest;
import com.loc.electronics_store.dto.request.category.CategoryCreationRequest;
import com.loc.electronics_store.dto.request.category.CategoryUpdateRequest;
import com.loc.electronics_store.dto.response.ApiResponse;
import com.loc.electronics_store.dto.response.brand.BrandResponse;
import com.loc.electronics_store.dto.response.category.CategoryResponse;
import com.loc.electronics_store.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/categories")
public class CategoryController {
    CategoryService categoryService;

    @GetMapping
    ApiResponse<Page<CategoryResponse>> getAll(@RequestParam int page,
                                               @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);

        return ApiResponse.<Page<CategoryResponse>>builder()
                .result(categoryService.getAll(pageable))
                .build();
    }

    @PostMapping
    ApiResponse<CategoryResponse> create(@RequestBody CategoryCreationRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<CategoryResponse> update(@PathVariable Long id, @RequestBody CategoryUpdateRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.update(id, request))
                .build();
    }
}

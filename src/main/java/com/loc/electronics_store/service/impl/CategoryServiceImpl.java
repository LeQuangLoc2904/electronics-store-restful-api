package com.loc.electronics_store.service.impl;


import com.loc.electronics_store.dto.request.category.CategoryCreationRequest;
import com.loc.electronics_store.dto.request.category.CategoryUpdateRequest;
import com.loc.electronics_store.dto.response.category.CategoryResponse;
import com.loc.electronics_store.entity.Brand;
import com.loc.electronics_store.entity.Category;
import com.loc.electronics_store.exception.AppException;
import com.loc.electronics_store.exception.ErrorCode;
import com.loc.electronics_store.mapper.CategoryMapper;
import com.loc.electronics_store.repository.BrandRepository;
import com.loc.electronics_store.repository.CategoryRepository;
import com.loc.electronics_store.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    BrandRepository brandRepository;

    @Override
    public Page<CategoryResponse> getAll(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);

        return categories.map(categoryMapper::toCategoryResponse);
    }

    @Override
    public CategoryResponse create(CategoryCreationRequest request) {
        Category category = categoryMapper.toCategory(request);

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse update(Long id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.CATEGORY_NOTEXISTED)
        );

        categoryMapper.updateCategory(request, category);

        category.setBrands(brandRepository.findAllById(request.getBrandIds()));

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse deleteBrandFromCategory(Long categoryId, Long brandId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()-> new AppException(ErrorCode.CATEGORY_NOTEXISTED)
        );

        List<Brand> brands = category.getBrands();

        for (Brand brand : brands) {
            if (brand.getId().equals(brandId)) {
                brands.remove(brand); break;
            }
        }

        category.setBrands(brands);

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }
}

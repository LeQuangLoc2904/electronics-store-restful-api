package com.loc.electronics_store.service.impl;


import com.loc.electronics_store.dto.request.category.CategoryCreationRequest;
import com.loc.electronics_store.dto.request.category.CategoryUpdateRequest;
import com.loc.electronics_store.dto.response.category.CategoryResponse;
import com.loc.electronics_store.entity.Category;
import com.loc.electronics_store.exception.AppException;
import com.loc.electronics_store.exception.ErrorCode;
import com.loc.electronics_store.mapper.CategoryMapper;
import com.loc.electronics_store.repository.CategoryRepository;
import com.loc.electronics_store.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

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

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }
}

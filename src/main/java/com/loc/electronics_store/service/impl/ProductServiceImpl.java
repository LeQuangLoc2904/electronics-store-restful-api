package com.loc.electronics_store.service.impl;

import com.loc.electronics_store.dto.request.product.ProductCreationRequest;
import com.loc.electronics_store.dto.request.product.ProductUpdateRequest;
import com.loc.electronics_store.dto.response.product.ProductResponse;
import com.loc.electronics_store.entity.Brand;
import com.loc.electronics_store.entity.Category;
import com.loc.electronics_store.entity.Product;
import com.loc.electronics_store.exception.AppException;
import com.loc.electronics_store.exception.ErrorCode;
import com.loc.electronics_store.mapper.ProductMapper;
import com.loc.electronics_store.repository.*;
import com.loc.electronics_store.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductServiceImpl implements ProductService {

    final ProductRepository productRepository;
    final ProductMapper productMapper;
    final CategoryRepository categoryRepository;
    final BrandRepository brandRepository;
    final ProductAttributeRepository productAttributeRepository;
    final ProductImageRepository productImageRepository;

    @Override
    public Page<ProductResponse> getAll(Pageable pageable) {
        Page<Product> products = productRepository.findAllByDeletedFalse(pageable);
        return products.map(productMapper::toResponse);
    }

    @Override
    public ProductResponse createProduct(ProductCreationRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOTEXISTED));
        Brand brand = brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOTEXISTED));

        Product newProduct = productMapper.toEntity(productRequest);
        newProduct.setCategory(category);
        newProduct.setBrand(brand);

        newProduct = productRepository.save(newProduct);
        return productMapper.toResponse(newProduct);
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        Product existingProduct = productRepository.findByIdAndDeletedFalse(productId);
        if (existingProduct == null) {
            throw new AppException(ErrorCode.PRODUCT_NOTEXISTED);
        }

        return productMapper.toResponse(existingProduct);
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest updateRequest) {
        Product existingProduct = productRepository.findByIdAndDeletedFalse(productId);
        if (existingProduct != null) {
            productImageRepository.deleteByProduct_Id(productId);
            productAttributeRepository.deleteByProduct_Id(productId);
            productMapper.updateEntityFromDto(updateRequest, existingProduct);

            return productMapper.toResponse(productRepository.save(existingProduct));
        }
        else throw new AppException(ErrorCode.PRODUCT_NOTEXISTED);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product existingProduct = productRepository.findByIdAndDeletedFalse(productId);
        if (existingProduct != null) {
            productRepository.delete(existingProduct);
        }

    }
}

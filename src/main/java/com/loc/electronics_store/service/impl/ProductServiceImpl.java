package com.loc.electronics_store.service.impl;

import com.loc.electronics_store.dto.request.product.ProductCreationRequest;
import com.loc.electronics_store.dto.request.product.ProductUpdateRequest;
import com.loc.electronics_store.dto.response.product.ProductResponse;
import com.loc.electronics_store.entity.Brand;
import com.loc.electronics_store.entity.Category;
import com.loc.electronics_store.entity.Product;
import com.loc.electronics_store.mapper.ProductMapper;
import com.loc.electronics_store.repository.*;
import com.loc.electronics_store.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;


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
    public ProductResponse createProduct(ProductCreationRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + productRequest.getCategoryId()));
        Brand brand = brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + productRequest.getBrandId()));

        Product newProduct = productMapper.toEntity(productRequest);
        newProduct.setCategory(category);
        newProduct.setBrand(brand);

        newProduct = productRepository.save(newProduct);
        return productMapper.toResponse(newProduct);
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        return productMapper.toResponse(productRepository.findByIdAndDeletedFalse(productId));
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

        return null;
    }

    @Override
    public void deleteProduct(Long productId) {
        Product existingProduct = productRepository.findByIdAndDeletedFalse(productId);
        if (existingProduct == null) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        productRepository.delete(existingProduct);
    }
}

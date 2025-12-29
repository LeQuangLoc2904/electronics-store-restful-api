package com.loc.electronics_store.service;

import com.loc.electronics_store.dto.request.product.ProductCreationRequest;
import com.loc.electronics_store.dto.request.product.ProductUpdateRequest;
import com.loc.electronics_store.dto.response.product.ProductResponse;

public interface ProductService {
    ProductResponse createProduct(ProductCreationRequest productRequest);
    ProductResponse getProductById(Long productId);
    ProductResponse updateProduct(Long productId, ProductUpdateRequest updateRequest);
    void deleteProduct(Long productId);
}

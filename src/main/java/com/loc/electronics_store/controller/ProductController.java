package com.loc.electronics_store.controller;

import com.loc.electronics_store.dto.request.product.ProductCreationRequest;
import com.loc.electronics_store.dto.request.product.ProductUpdateRequest;
import com.loc.electronics_store.dto.response.ApiResponse;
import com.loc.electronics_store.dto.response.product.ProductResponse;
import com.loc.electronics_store.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/products")
public class ProductController {

    final ProductServiceImpl productService;

    @GetMapping
    ApiResponse<List<ProductResponse>> getAll() {
        List<ProductResponse> productResponses = productService.getAll();

        return ApiResponse.<List<ProductResponse>>builder()
                .result(productResponses)
                .build();
    }

    @PostMapping
    ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductCreationRequest request) {
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productService.createProduct(request));
        return apiResponse;
    }

    @GetMapping("/{id}")
    ApiResponse<ProductResponse> getProductById(@PathVariable Long id) {
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productService.getProductById(id));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductUpdateRequest updateRequest) {
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productService.updateProduct(id, updateRequest));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteProduct(@PathVariable Long id) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        productService.deleteProduct(id);
        apiResponse.setResult("Delete successfully");
        return apiResponse;
    }
}

package com.loc.electronics_store.controller;

import com.loc.electronics_store.dto.request.product.ProductCreationRequest;
import com.loc.electronics_store.dto.request.product.ProductFilterRequest;
import com.loc.electronics_store.dto.request.product.ProductUpdateRequest;
import com.loc.electronics_store.dto.response.ApiResponse;
import com.loc.electronics_store.dto.response.product.ProductResponse;
import com.loc.electronics_store.service.impl.ProductAttributeFilterService;
import com.loc.electronics_store.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/products")
public class ProductController {

    final ProductServiceImpl productService;
    final ProductAttributeFilterService attributeFilterService;

    @GetMapping
    ApiResponse<Page<ProductResponse>> getAll(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponse> productResponses = productService.getAll(pageable);

        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productResponses)
                .build();
    }

    @GetMapping("/category")
    ApiResponse<Page<ProductResponse>> getAllByCategoryId(@RequestParam Long categoryId,
                                                          @RequestParam int page,
                                                          @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponse> productResponses = productService.getAllByCategoryId(categoryId, pageable);

        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productResponses)
                .build();
    }

    /**
     * 🔥 ENDPOINT CHÍNH: Lọc sản phẩm theo nhiều tiêu chí
     *
     * Phương thức: POST (vì request body chứa nhiều filter criteria)
     * URL: /api/products/filter
     *
     * Request Example:
     * {
     *   "categoryId": 1,
     *   "brandIds": [1, 2],
     *   "minPrice": 5000000,
     *   "maxPrice": 20000000,
     *   "searchKeyword": "iPhone",
     *   "attributes": {
     *     "RAM": ["8GB", "12GB"],
     *     "Chip": ["A17 Pro"]
     *   },
     *   "page": 0,
     *   "size": 20,
     *   "sortBy": "price",
     *   "sortDirection": "ASC"
     * }
     *
     * Response: Trang sản phẩm kèm theo thông tin phân trang
     */
    @PostMapping("/filter")
    ApiResponse<Page<ProductResponse>> filterProducts(@RequestBody ProductFilterRequest filterRequest) {
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productService.filterProducts(filterRequest))
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

    /**
     * Lấy danh sách các options để hiển thị filter
     * Dùng khi load trang product để hiển thị các checkbox/radio button
     *
     * Response Example:
     * {
     *   "RAM": ["4GB", "6GB", "8GB", "12GB"],
     *   "Chip": ["A15 Bionic", "A16 Bionic", "A17 Pro"],
     *   "Màn hình": ["6.1 inch", "6.7 inch"]
     * }
     */
    @GetMapping("/filter/options")
    ApiResponse<Map<String, List<String>>> getFilterOptions() {
        return ApiResponse.<Map<String, List<String>>>builder()
                .result(attributeFilterService.getAllAttributeValues())
                .build();
    }

    /**
     * Lấy danh sách options filter cho một category cụ thể
     *
     * @param categoryId ID của category
     */
    @GetMapping("/filter/options/category")
    ApiResponse<Map<String, List<String>>> getFilterOptionsByCategory(@RequestParam Long categoryId) {
        return ApiResponse.<Map<String, List<String>>>builder()
                .result(attributeFilterService.getAttributeValuesByCategory(categoryId))
                .build();
    }
}

package com.loc.electronics_store.controller;


import com.loc.electronics_store.dto.request.RoleRequest;
import com.loc.electronics_store.dto.request.brand.BrandCreationRequest;
import com.loc.electronics_store.dto.request.brand.BrandUpdateRequest;
import com.loc.electronics_store.dto.response.ApiResponse;
import com.loc.electronics_store.dto.response.RoleResponse;
import com.loc.electronics_store.dto.response.brand.BrandResponse;
import com.loc.electronics_store.service.BrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("/api/brands")
public class BrandController {
    BrandService brandService;

    @GetMapping
    ApiResponse<List<BrandResponse>> getAll() {
        return ApiResponse.<List<BrandResponse>>builder()
                .result(brandService.getAll())
                .build();
    }

    @PostMapping
    ApiResponse<BrandResponse> create(@RequestBody BrandCreationRequest request) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.createBrand(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<BrandResponse> update(@PathVariable Long id, @RequestBody BrandUpdateRequest request) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.updateBrand(id, request))
                .build();
    }

}

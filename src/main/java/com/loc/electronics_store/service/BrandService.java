package com.loc.electronics_store.service;

import com.loc.electronics_store.dto.request.brand.BrandCreationRequest;
import com.loc.electronics_store.dto.request.brand.BrandUpdateRequest;
import com.loc.electronics_store.dto.response.brand.BrandResponse;

import java.util.List;

public interface BrandService {
    List<BrandResponse> getAll();
    BrandResponse createBrand(BrandCreationRequest request);
    BrandResponse updateBrand(Long id, BrandUpdateRequest request);
}

package com.loc.electronics_store.service.impl;

import com.loc.electronics_store.dto.request.brand.BrandCreationRequest;
import com.loc.electronics_store.dto.request.brand.BrandUpdateRequest;
import com.loc.electronics_store.dto.response.brand.BrandResponse;
import com.loc.electronics_store.entity.Brand;
import com.loc.electronics_store.exception.AppException;
import com.loc.electronics_store.exception.ErrorCode;
import com.loc.electronics_store.mapper.BrandMapper;
import com.loc.electronics_store.repository.BrandRepository;
import com.loc.electronics_store.service.BrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BrandServiceImpl implements BrandService {
    BrandRepository brandRepository;
    BrandMapper brandMapper;

    @Override
    public List<BrandResponse> getAll() {
        List<Brand> brands = brandRepository.findAll();

        return brands.stream().map(brandMapper::toBrandRepsonse).toList();
    }

    @Override
    public BrandResponse createBrand(BrandCreationRequest request) {
        Brand brand = brandMapper.toBrand(request);

        return brandMapper.toBrandRepsonse(brandRepository.save(brand));
    }

    @Override
    public BrandResponse updateBrand(Long id, BrandUpdateRequest request) {
        Brand brand = brandRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.BRAND_NOTEXISTED)
        );

        brandMapper.updateBrand(request, brand);

        return brandMapper.toBrandRepsonse(brandRepository.save(brand));
    }
}

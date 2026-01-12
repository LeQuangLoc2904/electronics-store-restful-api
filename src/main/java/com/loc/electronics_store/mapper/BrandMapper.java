package com.loc.electronics_store.mapper;

import com.loc.electronics_store.dto.request.brand.BrandCreationRequest;
import com.loc.electronics_store.dto.request.brand.BrandUpdateRequest;
import com.loc.electronics_store.dto.response.brand.BrandResponse;
import com.loc.electronics_store.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toBrand(BrandCreationRequest request);
    BrandResponse toBrandRepsonse(Brand entity);
    void updateBrand(BrandUpdateRequest request, @MappingTarget Brand brand);
}

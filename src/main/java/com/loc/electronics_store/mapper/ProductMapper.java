package com.loc.electronics_store.mapper;


import com.loc.electronics_store.dto.request.product.AttributeRequest;
import com.loc.electronics_store.dto.request.product.ProductCreationRequest;
import com.loc.electronics_store.dto.request.product.ProductUpdateRequest;
import com.loc.electronics_store.dto.response.product.AttributeResponse;
import com.loc.electronics_store.dto.response.product.ProductResponse;
import com.loc.electronics_store.entity.Product;
import com.loc.electronics_store.entity.ProductAttribute;
import com.loc.electronics_store.entity.ProductImage;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "brand.id", source = "brandId")
    @Mapping(target = "images", source = "imageUrls", qualifiedByName = "mapImageUrlsToProductImages")
    @Mapping(target = "attributes", source = "attributes", qualifiedByName = "mapAttributesToProductAttributes")
    Product toEntity(ProductCreationRequest request);

    // New: update existing product from update request. Only set fields when source is non-null.
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "brand.id", source = "brandId")
    @Mapping(target = "images", source = "imageUrls", qualifiedByName = "mapImageUrlsToProductImages")
    @Mapping(target = "attributes", source = "attributes", qualifiedByName = "mapAttributesToProductAttributes")
    void updateEntityFromDto(ProductUpdateRequest request, @MappingTarget Product product);

    @Named("mapImageUrlsToProductImages")
    default List<ProductImage> mapImageUrlsToProductImages(List<String> imageUrls) {
        if (imageUrls == null) return null;

        return imageUrls.stream()
                .map(url -> ProductImage.builder()
                          .imageUrl(url)
                          .build()
                ).collect(Collectors.toList());
    }

    @Named("mapAttributesToProductAttributes")
    default List<ProductAttribute> mapAttributesToProductAttributes(List<AttributeRequest> attributes) {
        if (attributes == null) return null;
        return attributes.stream()
                .map(attr -> ProductAttribute.builder()
                            .name(attr.getName())
                            .value(attr.getValue())
                            .build()
                ).collect(Collectors.toList());
    }

    // Single after-mapping method used for both create and update mappings. It only needs the target entity.
    @AfterMapping
    default void linkProductToImagesAndAttributes(@MappingTarget Product product) {
        if (product.getImages() != null) {
            product.getImages().forEach(img -> img.setProduct(product));
        }

        if (product.getAttributes() != null) {
            product.getAttributes().forEach(attr -> attr.setProduct(product));
        }
    }

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "images", source = "images", qualifiedByName = "mapProductImagesToImageUrls")
    @Mapping(target = "attributes", source = "attributes", qualifiedByName = "mapProductAttributesToAttributes")
    ProductResponse toResponse(Product product);

    @Named("mapProductImagesToImageUrls")
    default List<String> mapProductImagesToImageUrls(List<ProductImage> images) {
        if (images == null) return null;

        return images.stream()
                .map(image -> image.getImageUrl())
                .collect(Collectors.toList());
    }

    @Named("mapProductAttributesToAttributes")
    default List<AttributeResponse> mapProductAttributesToAttributes(List<ProductAttribute> attributes) {
        if (attributes == null) return null;
        return attributes.stream()
                .map(attr -> AttributeResponse.builder()
                            .name(attr.getName())
                            .value(attr.getValue())
                            .build()
                ).collect(Collectors.toList());
    }
}

package com.loc.electronics_store.mapper;


import com.loc.electronics_store.dto.request.product.AttributeRequest;
import com.loc.electronics_store.dto.request.product.ProductCreationRequest;
import com.loc.electronics_store.dto.response.product.AttributeResponse;
import com.loc.electronics_store.dto.response.product.ProductResponse;
import com.loc.electronics_store.entity.Product;
import com.loc.electronics_store.entity.ProductAttribute;
import com.loc.electronics_store.entity.ProductImage;
import jdk.jfr.Name;
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
        return attributes.stream()
                .map(attr -> ProductAttribute.builder()
                            .name(attr.getName())
                            .value(attr.getValue())
                            .build()
                ).collect(Collectors.toList());
    }

    @AfterMapping
    default void linkProductToImagesAndAttributes(ProductCreationRequest request, @MappingTarget Product product) {
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
        return attributes.stream()
                .map(attr -> AttributeResponse.builder()
                            .name(attr.getName())
                            .value(attr.getValue())
                            .build()
                ).collect(Collectors.toList());
    }
}

package com.loc.electronics_store.dto.request.brand;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandCreationRequest {
    String name;
    String logoUrl;
}

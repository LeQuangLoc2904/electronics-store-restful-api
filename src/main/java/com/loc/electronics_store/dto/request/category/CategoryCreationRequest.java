package com.loc.electronics_store.dto.request.category;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryCreationRequest {
    String name;
    String slug;
    String description;
}

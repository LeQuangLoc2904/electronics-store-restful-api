package com.loc.electronics_store.dto.request.product;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeRequest {
    @NotBlank(message = "ATTRIBUTENAME_NOTEMPTY")
    private String name;

    @NotBlank(message = "ATTRIBUTEVALUE_NOTEMPTY")
    private String value;
}

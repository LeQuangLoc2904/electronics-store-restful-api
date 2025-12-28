package com.loc.electronics_store.dto.request.product;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeRequest {
    @NotBlank(message = "Tên thuộc tính không được để trống")
    private String name;

    @NotBlank(message = "Giá trị thuộc tính không được để trống")
    private String value;
}

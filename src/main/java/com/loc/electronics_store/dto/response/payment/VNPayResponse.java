package com.loc.electronics_store.dto.response.payment;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VNPayResponse {
    public String code;
    public String message;
    public String paymentUrl;
}

package com.loc.electronics_store.controller;


import com.loc.electronics_store.dto.response.ApiResponse;
import com.loc.electronics_store.dto.response.payment.VNPayResponse;
import com.loc.electronics_store.service.impl.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/payment")
public class PaymentController {

    PaymentService paymentService;

    @GetMapping("/vn-pay")
    public ApiResponse<VNPayResponse> pay(HttpServletRequest request) {
        return ApiResponse.<VNPayResponse>builder()
                .code(200)
                .message("Success")
                .result(paymentService.createVnPayPayment(request))
                .build();
    }

    @GetMapping("/vn-pay-callback")
    public ApiResponse<VNPayResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            return ApiResponse.<VNPayResponse>builder()
                    .code(200)
                    .message("Success")
                    .result(VNPayResponse.builder()
                            .code("00")
                            .message("Success")
                            .paymentUrl("")
                            .build())
                    .build();
        } else {
            return ApiResponse.<VNPayResponse>builder()
                    .code(400)
                    .message("Failed")
                    .result(null)
                    .build();        }
    }
}

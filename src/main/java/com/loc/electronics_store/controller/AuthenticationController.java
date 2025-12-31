package com.loc.electronics_store.controller;

import com.loc.electronics_store.dto.request.auth.AuthenticationRequest;
import com.loc.electronics_store.dto.response.ApiResponse;
import com.loc.electronics_store.dto.response.auth.AuthenticationRepsonse;
import com.loc.electronics_store.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/auth")
public class AuthenticationController {

    final AuthenticationService authenticationService;

    @PostMapping("/log-in")
    ApiResponse<AuthenticationRepsonse> authenticate(@RequestBody AuthenticationRequest request) {
        ApiResponse<AuthenticationRepsonse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(AuthenticationRepsonse.builder()
                        .authenticated(authenticationService.authenticate(request))
                        .build());

        return apiResponse;
    }
}

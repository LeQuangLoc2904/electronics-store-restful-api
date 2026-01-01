package com.loc.electronics_store.controller;

import com.loc.electronics_store.dto.request.IntrospectRequest;
import com.loc.electronics_store.dto.request.auth.AuthenticationRequest;
import com.loc.electronics_store.dto.response.ApiResponse;
import com.loc.electronics_store.dto.response.IntrospectResponse;
import com.loc.electronics_store.dto.response.auth.AuthenticationRepsonse;
import com.loc.electronics_store.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/auth")
public class AuthenticationController {

    final AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationRepsonse> authenticate(@RequestBody AuthenticationRequest request) {
        ApiResponse<AuthenticationRepsonse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(authenticationService.authenticate(request));

        return apiResponse;
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        ApiResponse<IntrospectResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(authenticationService.introspect(request));

        return apiResponse;
    }
}

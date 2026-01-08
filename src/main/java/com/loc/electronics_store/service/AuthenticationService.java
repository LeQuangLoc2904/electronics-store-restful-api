package com.loc.electronics_store.service;

import com.loc.electronics_store.dto.request.IntrospectRequest;
import com.loc.electronics_store.dto.request.LogoutRequest;
import com.loc.electronics_store.dto.request.RefreshRequest;
import com.loc.electronics_store.dto.request.auth.AuthenticationRequest;
import com.loc.electronics_store.dto.response.IntrospectResponse;
import com.loc.electronics_store.dto.response.auth.AuthenticationRepsonse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationRepsonse authenticate(AuthenticationRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    void logout(LogoutRequest request) throws ParseException, JOSEException;
    AuthenticationRepsonse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}

package com.loc.electronics_store.service;

import com.loc.electronics_store.dto.request.auth.AuthenticationRequest;

public interface AuthenticationService {
    boolean authenticate(AuthenticationRequest request);
}

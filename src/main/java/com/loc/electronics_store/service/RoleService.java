package com.loc.electronics_store.service;

import com.loc.electronics_store.dto.request.RoleRequest;
import com.loc.electronics_store.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest request);
    List<RoleResponse> getAll();
    void delete(String role);
}

package com.loc.electronics_store.service;

import com.loc.electronics_store.dto.request.PermissionRequest;
import com.loc.electronics_store.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);
    List<PermissionResponse> getAll();
    void delete(String permission);
}

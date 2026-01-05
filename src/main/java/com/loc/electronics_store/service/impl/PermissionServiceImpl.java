package com.loc.electronics_store.service.impl;

import com.loc.electronics_store.dto.request.PermissionRequest;
import com.loc.electronics_store.dto.response.PermissionResponse;
import com.loc.electronics_store.entity.Permission;
import com.loc.electronics_store.mapper.PermissionMapper;
import com.loc.electronics_store.repository.PermissionRepository;
import com.loc.electronics_store.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionServiceImpl implements PermissionService {
    final PermissionRepository permissionRepository;
    final PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(
                permission -> permissionMapper.toPermissionResponse(permission)
        ).toList();
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }

}

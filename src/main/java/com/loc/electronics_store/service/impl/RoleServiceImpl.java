package com.loc.electronics_store.service.impl;

import com.loc.electronics_store.dto.request.RoleRequest;
import com.loc.electronics_store.dto.response.RoleResponse;
import com.loc.electronics_store.mapper.RoleMapper;
import com.loc.electronics_store.repository.PermissionRepository;
import com.loc.electronics_store.repository.RoleRepository;
import com.loc.electronics_store.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleServiceImpl implements RoleService {
    final RoleRepository roleRepository;
    final PermissionRepository permissionRepository;
    final RoleMapper roleMapper;

    @Override
    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();

        return roles.stream().map(
                role -> roleMapper.toRoleResponse(role)
        ).toList();
    }

    @Override
    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}

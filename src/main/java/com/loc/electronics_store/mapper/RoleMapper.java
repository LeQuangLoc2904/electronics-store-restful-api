package com.loc.electronics_store.mapper;

import com.loc.electronics_store.dto.request.PermissionRequest;
import com.loc.electronics_store.dto.request.RoleRequest;
import com.loc.electronics_store.dto.response.PermissionResponse;
import com.loc.electronics_store.dto.response.RoleResponse;
import com.loc.electronics_store.entity.Permission;
import com.loc.electronics_store.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}

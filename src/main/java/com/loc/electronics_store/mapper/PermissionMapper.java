package com.loc.electronics_store.mapper;

import com.loc.electronics_store.dto.request.PermissionRequest;
import com.loc.electronics_store.dto.request.user.UserCreationRequest;
import com.loc.electronics_store.dto.request.user.UserUpdateRequest;
import com.loc.electronics_store.dto.response.PermissionResponse;
import com.loc.electronics_store.dto.response.user.UserResponse;
import com.loc.electronics_store.entity.Permission;
import com.loc.electronics_store.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}

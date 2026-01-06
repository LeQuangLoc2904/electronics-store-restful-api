package com.loc.electronics_store.mapper;

import com.loc.electronics_store.dto.request.user.UserCreationRequest;
import com.loc.electronics_store.dto.request.user.UserUpdateRequest;
import com.loc.electronics_store.dto.response.user.UserResponse;
import com.loc.electronics_store.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUserFromRequest(UserUpdateRequest request, @MappingTarget User user);
}

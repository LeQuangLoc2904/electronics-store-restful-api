package com.loc.electronics_store.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @NotBlank(message = "USERNAME_NOT_EMPTY")
    String username;

    @NotBlank(message = "PASSWORD_NOT_EMPTY")
    String password;

    String email;

    @NotBlank(message = "FULLNAME_NOT_EMPTY")
    String fullName;
    String phone;
    String address;
    List<String> roles;
}

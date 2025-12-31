package com.loc.electronics_store.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequest {

    @NotBlank(message = "USERNAME_NOT_EMPTY")
    private String username;

    @NotBlank(message = "PASSWORD_NOT_EMPTY")
    private String password;

    private String email;

    @NotBlank(message = "FULLNAME_NOT_EMPTY")
    private String fullName;

    private String phone;

    private String address;
}

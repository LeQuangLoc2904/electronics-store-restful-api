package com.loc.electronics_store.dto.request.user;

import com.loc.electronics_store.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserCreationRequest {

    @NotBlank(message = "USERNAME_NOT_EMPTY")
    String username;

    @NotBlank(message = "PASSWORD_NOT_EMPTY")
    String password;

    String email;

    @NotBlank(message = "FULLNAME_NOT_EMPTY")
    String fullName;

    String phone;

    String address;

    @DobConstraint(min = 3, message = "INVALID_DOB")
    LocalDate dob;
}

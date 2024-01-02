package com.tai.gky.demoKemal.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticateRequestDto {

    @NotEmpty(message = "Username cannot be empty.")
    private String username;
    @Size(min = 4, max = 12, message = "Password must be between 4 and 12 char.")
    @NotEmpty(message = "Password cannot be empty.")
    private String password;
}

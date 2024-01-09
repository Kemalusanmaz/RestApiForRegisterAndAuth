package com.tai.gky.demoKemal.Dto;

import com.tai.gky.demoKemal.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialsDto {
    private String username;
    private String password;
    private String email;
    private String lastname;
    private String firstname;
    private Role role;
}

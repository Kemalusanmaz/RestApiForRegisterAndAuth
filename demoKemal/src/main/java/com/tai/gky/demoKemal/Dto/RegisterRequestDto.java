package com.tai.gky.demoKemal.Dto;


import com.tai.gky.demoKemal.Enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Kullanıcı bilgilerini temsil eden bir model (DTO => Data Transfer Object)
//Api üzerinden JSON isteği bu bilgilerin karşılığıdır.

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {


    private String username;
    private String password;
    private String email;
    private String lastname;
    private String firstname;
    private Role role;

}

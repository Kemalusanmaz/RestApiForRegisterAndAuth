package com.tai.gky.demoKemal.Dto;


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

    private String id;
    @NotEmpty(message = "Username cannot be empty.")
    private String username;
    @Size(min = 4, max = 12, message = "Password must be between 4 and 12 char.")
    @NotEmpty(message = "Password cannot be empty.")
    private String password;
    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Please enter a valid email address.")
    private String email;
    @NotEmpty(message = "Lastname cannot be empty.")
    private String lastname;
    @NotEmpty(message = "Firstname cannot be empty.")
    private String firstname;
    private String roles;
}

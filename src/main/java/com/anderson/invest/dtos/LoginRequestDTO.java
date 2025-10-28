package com.anderson.invest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequestDTO {
    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "Formato de email inválido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha precisa ter no minimo 8 caracteres")
    private String password;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String email, String passwarod) {
        this.email = email;
        this.password = passwarod;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

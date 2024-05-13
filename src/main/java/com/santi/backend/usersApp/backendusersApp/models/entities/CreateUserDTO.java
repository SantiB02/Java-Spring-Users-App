package com.santi.backend.usersApp.backendusersApp.models.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class CreateUserDTO implements Serializable {

    @NotBlank(message = "Username can't be empty!")
    @Size(min = 4, max = 15)
    final String username;

    @NotBlank(message = "Password can't be empty!")
    @Size(min = 4, max = 15)
    final String password;

    @NotBlank(message = "Email can't be empty!")
    @Email
    final String email;

    public CreateUserDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}

package com.santi.backend.usersApp.backendusersApp.models.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class UpdateUserDTO implements Serializable {

    @NotBlank(message = "Username can't be empty!")
    @Size(min = 4, max = 15)
    final String username;

    @NotBlank(message = "Password can't be empty!")
    @Size(min = 4, max = 15)
    final String password;

    public UpdateUserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

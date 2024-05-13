package com.santi.backend.usersApp.backendusersApp.models.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class UserDTO implements Serializable {

    @NotBlank(message = "Username can't be empty!")
    @Size(min = 4, max = 15)
    final String username;

    @NotBlank(message = "Email can't be empty!")
    @Email
    final String email;

    public UserDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}

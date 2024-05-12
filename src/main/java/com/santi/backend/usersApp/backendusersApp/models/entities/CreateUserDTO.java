package com.santi.backend.usersApp.backendusersApp.models.entities;

import java.io.Serializable;

public class CreateUserDTO implements Serializable {
    final String username;
    final String password;
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

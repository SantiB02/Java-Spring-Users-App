package com.santi.backend.usersApp.backendusersApp.models.entities;

import java.io.Serializable;

public class UserDTO implements Serializable {
    final String username;
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

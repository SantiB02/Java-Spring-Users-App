package com.santi.backend.usersApp.backendusersApp.models.entities;

import java.io.Serializable;

public class UpdateUserDTO implements Serializable {
    final String username;
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

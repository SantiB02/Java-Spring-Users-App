package com.santi.backend.usersApp.backendusersApp.services;

import com.santi.backend.usersApp.backendusersApp.models.entities.CreateUserDTO;
import com.santi.backend.usersApp.backendusersApp.models.entities.User;
import com.santi.backend.usersApp.backendusersApp.models.entities.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO findById(Long id);

    User save(CreateUserDTO createUserDTO);

    void remove(Long id);
}

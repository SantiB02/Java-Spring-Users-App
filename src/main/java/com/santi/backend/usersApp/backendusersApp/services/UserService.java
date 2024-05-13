package com.santi.backend.usersApp.backendusersApp.services;

import com.santi.backend.usersApp.backendusersApp.models.entities.CreateUserDTO;
import com.santi.backend.usersApp.backendusersApp.models.entities.UpdateUserDTO;
import com.santi.backend.usersApp.backendusersApp.models.entities.User;
import com.santi.backend.usersApp.backendusersApp.models.entities.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    User findById(Long id);

    User create(CreateUserDTO createUserDTO);

    User save(User user);

    User update(UpdateUserDTO updateUserDTO, Long id);

    void remove(Long id);
}

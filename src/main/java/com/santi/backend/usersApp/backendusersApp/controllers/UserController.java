package com.santi.backend.usersApp.backendusersApp.controllers;

import com.santi.backend.usersApp.backendusersApp.models.entities.CreateUserDTO;
import com.santi.backend.usersApp.backendusersApp.models.entities.UserDTO;
import com.santi.backend.usersApp.backendusersApp.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<UserDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> show(@PathVariable Long id) {
        try {
            UserDTO userDTO = service.findById(id);
            return ResponseEntity.ok(userDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<CreateUserDTO> create(CreateUserDTO createUserDTO) {


        return service.save(createUserDTO);
    }
}

package com.santi.backend.usersApp.backendusersApp.controllers;

import com.santi.backend.usersApp.backendusersApp.models.entities.CreateUserDTO;
import com.santi.backend.usersApp.backendusersApp.models.entities.UpdateUserDTO;
import com.santi.backend.usersApp.backendusersApp.models.entities.User;
import com.santi.backend.usersApp.backendusersApp.models.entities.UserDTO;
import com.santi.backend.usersApp.backendusersApp.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            //obtengo el usuario de la base de datos y lo mapeo a un DTO
            User foundUser = service.findById(id);
            UserDTO userDto = new UserDTO(foundUser.getUsername(), foundUser.getEmail());

            return ResponseEntity.ok(userDto);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody CreateUserDTO createUserDTO) {
        try {
            User userDB = service.create(createUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDB);
        } catch (EntityNotFoundException e) {
         return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody UpdateUserDTO updateUserDTO, @PathVariable Long id) {

        try {
            User userDB = service.update(updateUserDTO, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDB);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> remove(@PathVariable Long id) {
        try {
            service.remove(id);
            return ResponseEntity.noContent().build(); //HTTP status: 204
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

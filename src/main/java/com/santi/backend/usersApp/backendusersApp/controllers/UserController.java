package com.santi.backend.usersApp.backendusersApp.controllers;

import com.santi.backend.usersApp.backendusersApp.models.entities.CreateUserDTO;
import com.santi.backend.usersApp.backendusersApp.models.entities.UpdateUserDTO;
import com.santi.backend.usersApp.backendusersApp.models.entities.User;
import com.santi.backend.usersApp.backendusersApp.models.entities.UserDTO;
import com.santi.backend.usersApp.backendusersApp.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //@Valid y BindingResult sirven para validar el objeto que se pasa por parámetro:
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateUserDTO createUserDTO, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }

        try {
            User userDB = service.create(createUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDB);
        } catch (EntityNotFoundException e) {
         return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateUserDTO updateUserDTO,
                                    BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validation(result);
        }

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

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "The field " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}

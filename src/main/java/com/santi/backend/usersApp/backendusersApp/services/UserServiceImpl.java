package com.santi.backend.usersApp.backendusersApp.services;

import com.santi.backend.usersApp.backendusersApp.models.entities.CreateUserDTO;
import com.santi.backend.usersApp.backendusersApp.models.entities.User;
import com.santi.backend.usersApp.backendusersApp.models.entities.UserDTO;
import com.santi.backend.usersApp.backendusersApp.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired //inyección de dependencias automática
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> usersList = (List<User>) repository.findAll();
        return usersList.stream()
            .map(entity -> new UserDTO(entity.getUsername(), entity.getEmail()))
            .collect(Collectors.toList()); //convierto las entidades User a DTOs para devolver
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        final Optional<User> foundUser = repository.findById(id);

        if (foundUser.isEmpty()) {
            throw new EntityNotFoundException("User with id " + id + " was not found");
        }
        final User userFromBD = foundUser.get(); //foundUser es de tipo Optional, necesitamos su valor
        return new UserDTO(userFromBD.getUsername(), userFromBD.getEmail());
    }

    @Override
    @Transactional
    public User save(CreateUserDTO createUserDTO) {
        User user = new User();
        //mapeo el DTO a una entidad User (también se pueden usar librerías como MapStruct o ModelMapper):
        user.setUsername(createUserDTO.getUsername());
        user.setPassword(createUserDTO.getPassword());
        user.setEmail(createUserDTO.getEmail());

        return repository.save(user);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }
}

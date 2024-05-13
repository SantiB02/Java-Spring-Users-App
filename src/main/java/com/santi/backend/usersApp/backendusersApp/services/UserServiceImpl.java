package com.santi.backend.usersApp.backendusersApp.services;

import com.santi.backend.usersApp.backendusersApp.models.entities.CreateUserDTO;
import com.santi.backend.usersApp.backendusersApp.models.entities.UpdateUserDTO;
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
    public User findById(Long id) {
        User foundUser = repository.findById(id).orElse(null);

        if (foundUser == null) {
            throw new EntityNotFoundException("User with id " + id + " was not found");
        }

        return foundUser;
    }

    @Override
    @Transactional
    public User create(CreateUserDTO createUserDTO) {
        User user = new User();
        //mapeo el DTO a una entidad User (también se pueden usar librerías como MapStruct o ModelMapper):
        user.setUsername(createUserDTO.getUsername());
        user.setPassword(createUserDTO.getPassword());
        user.setEmail(createUserDTO.getEmail());

        return repository.save(user);
    }

    @Override
    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public User update(UpdateUserDTO updateUserDTO, Long id) {
        User userDB = this.findById(id);

        userDB.setUsername(updateUserDTO.getUsername());
        userDB.setPassword(updateUserDTO.getPassword());

        return this.save(userDB);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        User userDB = this.findById(id); //si no se encuentra el usuario, se lanza una excepción (ver findById)

        repository.deleteById(userDB.getId());
    }
}

package com.santi.backend.usersApp.backendusersApp.repositories;

import com.santi.backend.usersApp.backendusersApp.models.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}

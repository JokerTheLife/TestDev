package com.example.demo.repo;

import com.example.demo.model.RolesUser;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select u from User u where u.login = :users")
    Optional<User> findById(String users);
}

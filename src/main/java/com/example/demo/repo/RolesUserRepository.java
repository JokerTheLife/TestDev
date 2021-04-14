package com.example.demo.repo;

import com.example.demo.model.Roles;
import com.example.demo.model.RolesUser;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface RolesUserRepository extends CrudRepository<RolesUser, Long> {

    @Query("select u from RolesUser u where u.users = :users")
    Iterable<RolesUser> findByUsers(User users);

    @Query("select u from RolesUser u where u.users = :users and u.rights = :role")
    RolesUser findByUsers(User user, Roles role);

    @Query("select u from RolesUser u where u.users = :users and u.rights = :role")
    RolesUser findByUser(User users, Roles role);
}

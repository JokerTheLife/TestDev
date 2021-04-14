package com.example.demo.repo;

import com.example.demo.model.ApplicationStatus;
import com.example.demo.model.RolesUser;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ApplicationStatusRepository extends CrudRepository<ApplicationStatus, Long> {
    @Query("select u from ApplicationStatus u where u.status = :status")
    Optional<ApplicationStatus> findById(String status);
}

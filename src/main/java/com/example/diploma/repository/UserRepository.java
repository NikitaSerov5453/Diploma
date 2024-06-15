package com.example.diploma.repository;


import com.example.diploma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, CustomUserRepository{

    @Query("FROM User u LEFT JOIN FETCH u.role WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);
//
//    @Query("FROM User u JOIN FETCH u.role")
//    List<User> findAll();
//
    @Query("FROM User u LEFT JOIN FETCH u.role WHERE u.id = :id")
    Optional<User> findUserById(@Param("id") UUID id);
}

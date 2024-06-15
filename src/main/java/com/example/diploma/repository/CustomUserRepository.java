package com.example.diploma.repository;

import com.example.diploma.dto.view.UserView;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomUserRepository {

    Optional<UserView> findUserByUsername(String username);

    Optional<UserView> findUserByUserID(UUID id);

    List<UserView> findAllUsers();
}

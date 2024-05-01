package com.example.diploma.repository;

import com.example.diploma.entity.EmailConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailConfigurationRepository extends JpaRepository<EmailConfiguration, UUID> {

}

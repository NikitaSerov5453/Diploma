package com.example.diploma.repository;

import com.example.diploma.entity.sql.SQLAuthorisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SQLAuthorisationRepository extends JpaRepository<SQLAuthorisation, UUID> {
}

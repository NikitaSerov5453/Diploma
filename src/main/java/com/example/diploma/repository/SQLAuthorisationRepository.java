package com.example.diploma.repository;

import com.example.diploma.entity.SQLAuthorisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SQLAuthorisationRepository extends JpaRepository<SQLAuthorisation, UUID> {

    List<SQLAuthorisation> findByReportId(UUID reportId);
}

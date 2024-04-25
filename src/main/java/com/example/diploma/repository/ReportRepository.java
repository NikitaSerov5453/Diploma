package com.example.diploma.repository;

import com.example.diploma.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {

    List<Report> findAllById(UUID id);

    List<Report> findReportById(UUID id);
}

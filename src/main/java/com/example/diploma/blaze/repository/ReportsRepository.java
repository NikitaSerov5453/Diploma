package com.example.diploma.blaze.repository;

import com.example.diploma.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportsRepository extends JpaRepository<Report, UUID>, CustomReportRepository {
}

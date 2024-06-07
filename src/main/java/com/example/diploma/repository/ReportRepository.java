package com.example.diploma.repository;

import com.example.diploma.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {

//    List<Report> findAllById(UUID id);

    @Query("FROM Report r LEFT JOIN r.addresses LEFT JOIN FETCH r.sqlAuthorisations WHERE r.id = :id")
//    @Query("SELECT r FROM Report r LEFT JOIN FETCH r.addresses a LEFT JOIN FETCH r.sqlAuthorisations s WHERE r.id = :id")
    Optional<Report> findReportById(@Param("id") UUID id);

    List<Report> findAllByReportCreator(String reportCreator);

}

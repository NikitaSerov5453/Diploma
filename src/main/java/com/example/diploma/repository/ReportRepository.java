package com.example.diploma.repository;

import com.example.diploma.entity.Report;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {

//    List<Report> findAllById(UUID id);

//    @Query("FROM Report r LEFT JOIN r.addresses JOIN r.sqlAuthorisations WHERE r.id = :id")
//    @Query("SELECT r " +
//            "FROM Report r " +
//            "LEFT JOIN FETCH r.addresses a " +
//            "LEFT JOIN FETCH r.sqlAuthorisations s " +
//            "LEFT JOIN FETCH s.sqlRequests sq " +
//            "WHERE r.id = :id")
    @Query(name = "SELECT r FROM Report r " +
            "LEFT JOIN FETCH r.addresses a " +
            "LEFT JOIN FETCH r.sqlAuthorisations s " +
            "LEFT JOIN FETCH s.sqlRequests sq WHERE r.id = :id" , nativeQuery = true)
    Optional<Report> findReportById(UUID id);

    List<Report> findAllByReportCreator(String reportCreator);

}

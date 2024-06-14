//package com.example.diploma.repository;
//
//import com.example.diploma.entity.Report;
//import io.micrometer.observation.ObservationFilter;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Query;
//import lombok.RequiredArgsConstructor;
//import org.hibernate.jpa.AvailableHints;
//import org.hibernate.jpa.QueryHints;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@Repository
//@RequiredArgsConstructor
//public class ReportEntityManagerRepository {
//
//    private final EntityManager entityManager;
//
//    public Optional<Report> findReportById(UUID id) {
//        String jpql = "SELECT DISTINCT r FROM Report r LEFT JOIN FETCH r.addresses a LEFT JOIN FETCH r.sqlAuthorisations s LEFT JOIN FETCH s.sqlRequests m WHERE r.id = :id";
//        Query query = entityManager.createQuery(jpql).setParameter("id", id);
//        return Optional.ofNullable((Report) query.getSingleResult());
//    }
//}

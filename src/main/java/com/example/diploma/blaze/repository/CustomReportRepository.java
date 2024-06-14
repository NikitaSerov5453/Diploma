package com.example.diploma.blaze.repository;

import com.example.diploma.blaze.view.ReportView;
import com.example.diploma.entity.Report;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomReportRepository {

    Optional<Report> findByReportId(UUID id);

    Optional<ReportView> searchByReportViewId(UUID id);

    List<Report> findAllReports();

    List<ReportView> findAllReportsView();
}

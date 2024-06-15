package com.example.diploma.repository;

import com.example.diploma.dto.view.ReportView;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomReportRepository {

    Optional<ReportView> findReportByReportId(UUID id);

    List<ReportView> findAllReportsView();

    List<ReportView> findReportsByReportCreator(String reportCreator);
}

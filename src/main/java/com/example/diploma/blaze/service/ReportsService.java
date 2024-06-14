package com.example.diploma.blaze.service;

import com.example.diploma.blaze.repository.ReportsRepository;
import com.example.diploma.blaze.view.ReportView;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportsService {

    private final ReportsRepository reportsRepository;

    public List<ReportView> findReports() {
        return reportsRepository.findAllReportsView();
    }

    public Optional<ReportView> findReportById(UUID id) {
        return reportsRepository.searchByReportViewId(id);
    }
}
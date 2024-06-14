package com.example.diploma.blaze.controller;

import com.example.diploma.blaze.repository.ReportsRepository;
import com.example.diploma.blaze.service.ReportsService;
import com.example.diploma.blaze.view.ReportView;
import com.example.diploma.entity.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController

@RequestMapping("/blaze")
@RequiredArgsConstructor
public class controller {

    private final ReportsRepository reportsRepository;

    private final ReportsService reportsService;


    @GetMapping
    public List<ReportView> blaze() {
        return reportsService.findReports();
    }

    @GetMapping("/{id}")
    public Optional<ReportView> searchByReportViewId(@PathVariable UUID id) {
        return reportsService.findReportById(id);
    }

}

package com.example.diploma.controller;

import com.example.diploma.dto.ReportDto;
import com.example.diploma.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ReportDto addReport(@RequestBody ReportDto reportDto) {
        return reportService.createNewReport(reportDto);
    }

    @GetMapping
    public List<ReportDto> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{id}")
    public Optional<ReportDto> getReportById(@PathVariable("id") UUID id) {
        return reportService.getReportByReportId(id);
    }
}

package com.example.diploma.controller;

import com.example.diploma.dto.view.ReportView;
import com.example.diploma.dto.ReportDto;
import com.example.diploma.service.ReportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/undefined")
    public ReportDto addReport(@Valid @RequestBody ReportDto reportDto) {
        return reportService.createNewReport(reportDto);
    }

    @GetMapping
    public List<ReportView> getAllReports() {
        return reportService.findReports();
    }

    @GetMapping("/all/{reportCreator}")
    public List<ReportView> getReportsByReportCreator(@PathVariable("reportCreator") String reportCreator) {
        return reportService.findReportsByReportCreator(reportCreator);
    }

    @GetMapping("/{id}")
    public Optional<ReportView> getReportById(@PathVariable("id") UUID id) {
        return reportService.findReportById(id);
    }

    @PutMapping("/update")
    public ReportDto updateReport(@RequestBody ReportDto reportDto) {
        return reportService.updateReport(reportDto);
    }

    @PutMapping("/stop/{id}")
    public void stopReport(@PathVariable("id") UUID id) {
        reportService.stopReport(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReport(@PathVariable("id") UUID id) {
        reportService.deleteReport(id);
    }
}

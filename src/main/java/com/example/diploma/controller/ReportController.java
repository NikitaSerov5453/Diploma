package com.example.diploma.controller;

import com.example.diploma.dto.ReportDto;
import com.example.diploma.quartz.schedule.MailScheduleService;
import com.example.diploma.service.ReportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    private final MailScheduleService mailScheduleService;


    @PostMapping
    public ReportDto addReport(@Valid @RequestBody ReportDto reportDto) {
        mailScheduleService.createSchedule(reportDto);
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

    @GetMapping("/running")
    public List<ReportDto> getAllRunning() {
        return mailScheduleService.getReportDtos();
    }

    @GetMapping("/running/{id}")
    private ReportDto getRunningReportById(@PathVariable("id") String id) {
        return mailScheduleService.getReportDto(id);
    }
}

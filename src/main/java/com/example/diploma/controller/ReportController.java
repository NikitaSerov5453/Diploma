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
@CrossOrigin
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    private final MailScheduleService mailScheduleService;


    @PostMapping("/undefined")
    public ReportDto addReport(@Valid @RequestBody ReportDto reportDto) {
        return reportService.createNewReport(reportDto);
    }

    @GetMapping
    public List<ReportDto> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/all/{reportCreator}")
    public List<ReportDto> getReportsByReportCreator(@PathVariable("reportCreator") String reportCreator) {
        return reportService.getAllReportsByReportCreator(reportCreator);
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

    @PutMapping("/running/stop/{id}")
    public void stopReport(@PathVariable("id") UUID id) {
        Optional<ReportDto> reportDto = reportService.getReportByReportId(id);
        mailScheduleService.stopSchedule(reportDto.get().getAutomatedReporting(), reportDto.get().getId());
    }

    @PutMapping("/update")
    public ReportDto updateReport(@RequestBody ReportDto reportDto) {
        return reportService.updateReport(reportDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReport(@PathVariable("id") UUID id) {
        reportService.deleteReport(id);
    }
}

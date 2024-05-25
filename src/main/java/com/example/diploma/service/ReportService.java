package com.example.diploma.service;

import com.example.diploma.dto.*;
import com.example.diploma.entity.Addressee;
import com.example.diploma.entity.Report;
import com.example.diploma.entity.SQLAuthorisation;
import com.example.diploma.mapper.ReportMapper;
import com.example.diploma.quartz.schedule.MailScheduleService;
import com.example.diploma.repository.ReportRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class ReportService {

    private final ReportRepository reportRepository;

    private final ReportMapper reportMapper;

    private final AddresseeService addresseeService;

    private final MailScheduleService mailScheduleService;

    private final SQLAuthorisationService sqlAuthorisationService;

    private final UserService userService;


    public ReportDto createNewReport(ReportDto reportDto) {
        reportDto.setAutomatedReporting(UUID.randomUUID());
        mailScheduleService.createSchedule(reportDto);
//        reportDto.setReportCreator(userService.getCurrentUser().getUsername());

        Report entity = reportMapper.toEntity(reportDto);
        return reportMapper.toDto(reportRepository.save(entity));
    }

    public ReportDto updateReport(ReportDto reportDto) {
        addresseeService.deleteAllAddresseesByReportId(reportDto.getId());
        sqlAuthorisationService.deleteAllSQLAuthorisationByReportId(reportDto.getId());
        mailScheduleService.deleteSchedule(reportDto.getAutomatedReporting(), reportDto.getId());

        Report entity = reportMapper.toEntity(reportDto);
        mailScheduleService.createSchedule(reportDto);

        return reportMapper.toDto(reportRepository.save(entity));
    }

    public void deleteReport(UUID reportId) {
        Optional<Report> reportDto = reportRepository.findReportById(reportId);
        addresseeService.deleteAllAddresseesByReportId(reportDto.get().getId());
        sqlAuthorisationService.deleteAllSQLAuthorisationByReportId(reportDto.get().getId());
        reportRepository.deleteById(reportId);
    }

    public List<ReportDto> getAllReports() {
        return reportRepository.findAll().stream()
                .map(reportMapper::toDto)
                .toList();
    }

    public Optional<ReportDto> getReportByReportId(UUID reportId) {
        return reportRepository.findReportById(reportId).map(reportMapper::toDto);
    }
}

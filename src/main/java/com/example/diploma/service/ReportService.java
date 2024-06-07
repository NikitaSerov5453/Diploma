package com.example.diploma.service;

import com.example.diploma.dto.*;
import com.example.diploma.entity.Report;
import com.example.diploma.mapper.ReportMapper;
import com.example.diploma.quartz.schedule.MailScheduleService;
import com.example.diploma.repository.ReportRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;


    public ReportDto createNewReport(ReportDto reportDto) {
        reportDto.setAutomatedReporting(UUID.randomUUID());
        mailScheduleService.createSchedule(reportDto);

        for (int i = 0; i < reportDto.getSqlAuthorisations().size(); i++) {
            reportDto
                    .getSqlAuthorisations()
                    .get(i)
                    .setPassword(
                    passwordEncoder.encode(reportDto
                            .getSqlAuthorisations()
                            .get(i)
                            .getPassword()));
        }

        Report entity = reportMapper.toEntity(reportDto);

        return reportMapper.toDto(reportRepository.save(entity));
    }

    public ReportDto updateReport(ReportDto reportDto) {
        addresseeService.deleteAllAddresseesByReportId(reportDto.getId());
        sqlAuthorisationService.deleteAllSQLAuthorisationByReportId(reportDto.getId());
        mailScheduleService.deleteSchedule(reportDto.getAutomatedReporting(), reportDto.getName());

        return createNewReport(reportDto);
    }

    public void deleteReport(UUID reportId) {
        Optional<Report> reportDto = reportRepository.findReportById(reportId);
        addresseeService.deleteAllAddresseesByReportId(reportDto.get().getId());
        sqlAuthorisationService.deleteAllSQLAuthorisationByReportId(reportDto.get().getId());
        mailScheduleService.deleteSchedule(reportDto.get().getAutomatedReporting(), reportDto.get().getName());
        reportRepository.deleteById(reportId);
    }

    public List<ReportDto> getAllReports() {
        return reportRepository
                .findAll()
                .stream()
                .map(reportMapper::toDto)
                .toList();
    }

    public Optional<ReportDto> getReportByReportId(UUID reportId) {
        return reportRepository.findReportById(reportId).map(reportMapper::toDto);
    }

    public List<ReportDto> getAllReportsByReportCreator(String reportCreator) {
        return reportRepository
                .findAllByReportCreator(reportCreator)
                .stream()
                .map(reportMapper::toDto)
                .toList();
    }
}

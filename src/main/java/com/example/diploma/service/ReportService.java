package com.example.diploma.service;


import com.example.diploma.dto.*;
import com.example.diploma.dto.view.ReportView;
import com.example.diploma.entity.Report;
import com.example.diploma.mapper.ReportMapper;
import com.example.diploma.quartz.schedule.MailScheduleService;
import com.example.diploma.repository.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
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


        Report entity = reportMapper.toEntity(reportDto);

        return reportMapper.toDto(reportRepository.save(entity));
    }

    public ReportDto updateReport(ReportDto reportDto) {
        addresseeService.deleteAllAddresseesByReportId(reportDto.getId());
        mailScheduleService.deleteSchedule(reportDto.getAutomatedReporting(), reportDto.getName());

        reportDto.setAutomatedReporting(UUID.randomUUID());
        mailScheduleService.createSchedule(reportDto);

        for (int i = 0; i < reportDto.getSqlAuthorisations().size(); i++) {
            if (!reportDto.getSqlAuthorisations()
                    .get(i)
                    .getPassword()
                    .equals(reportRepository
                            .findReportByReportId(reportDto.getId())
                            .get()
                            .getSqlAuthorisations()
                            .get(i).getPassword())) {
                reportDto.getSqlAuthorisations()
                        .get(i)
                        .setPassword(passwordEncoder.encode(reportDto
                                .getSqlAuthorisations()
                                .get(i)
                                .getPassword()));
            }
        }

        sqlAuthorisationService.deleteAllSQLAuthorisationByReportId(reportDto.getId());

        Report entity = reportMapper.toEntity(reportDto);

        return reportMapper.toDto(reportRepository.save(entity));
    }

    public void deleteReport(UUID reportId) {
        Optional<ReportView> reportView = reportRepository.findReportByReportId(reportId);

        addresseeService.deleteAllAddresseesByReportId(reportView.get().getId());
        sqlAuthorisationService.deleteAllSQLAuthorisationByReportId(reportView.get().getId());
        mailScheduleService.deleteSchedule(reportView.get().getAutomatedReporting(), reportView.get().getName());
        reportRepository.deleteById(reportId);
    }

    public List<ReportView> findReports() {
        return reportRepository.findAllReportsView();
    }

    public List<ReportView> findReportsByReportCreator(String reportCreator) {
        return reportRepository.findReportsByReportCreator(reportCreator);
    }

    public Optional<ReportView> findReportById(UUID id) {
        return reportRepository.findReportByReportId(id);
    }

    public void stopReport(UUID id) {
        Optional<ReportView> reportView = reportRepository.findReportByReportId(id);

        mailScheduleService.stopSchedule(reportView.get().getAutomatedReporting(), reportView.get().getName());
    }
}
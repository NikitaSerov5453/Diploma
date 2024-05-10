package com.example.diploma.service;

import com.example.diploma.dto.*;
import com.example.diploma.entity.Report;
import com.example.diploma.mapper.ReportMapper;
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

    public ReportDto createNewReport(ReportDto reportDto) {
        Report entity = reportMapper.toEntity(reportDto);
        return reportMapper.toDto(reportRepository.save(entity));
    }

    public List<ReportDto> getAllReports() {
        return reportRepository.findAll().stream()
                .map(reportMapper::toDto)
                .toList();
    }

    public Optional<ReportDto> getReportByReportId(UUID reportId) {
        return reportRepository.findReportById(reportId).map(reportMapper::toDto);
    }

    public ReportDto getReportById(UUID reportId) {
        return reportRepository.findById(reportId).map(reportMapper::toDto).orElse(null);
    }


}

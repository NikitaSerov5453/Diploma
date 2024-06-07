package com.example.diploma.service;

import com.example.diploma.entity.SQLAuthorisation;
import com.example.diploma.repository.SQLAuthorisationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SQLAuthorisationService {

    private final SQLAuthorisationRepository sqlAuthorisationRepository;

    private final SQLRequestService sqlRequestService;

    public void deleteAllSQLAuthorisationByReportId(UUID ReportId) {
        List<SQLAuthorisation> sqlAuthorisations = sqlAuthorisationRepository.findByReportId(ReportId);

        for (SQLAuthorisation sqlAuthorisation : sqlAuthorisations) {
            sqlRequestService.deleteAllRequestsByReportId(sqlAuthorisation.getId());
        }
        sqlAuthorisationRepository.deleteAll(sqlAuthorisations);
    }
}
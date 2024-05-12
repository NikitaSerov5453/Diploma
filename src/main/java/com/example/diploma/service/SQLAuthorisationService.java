package com.example.diploma.service;

import com.example.diploma.dto.SQLAuthorisationDto;
import com.example.diploma.entity.SQLAuthorisation;
import com.example.diploma.mapper.SQLAuthorisationMapper;
import com.example.diploma.repository.SQLAuthorisationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SQLAuthorisationService {

    private final SQLAuthorisationMapper sqlAuthorisationMapper;

    private final SQLAuthorisationRepository sqlAuthorisationRepository;

    private final PasswordEncoder passwordEncoder;

    private final SQLRequestService sqlRequestService;

    public SQLAuthorisationDto addSQLAuthorisation(SQLAuthorisationDto sqlAuthorisationDto) {
        String encodePassword = passwordEncoder.encode(sqlAuthorisationDto.getPassword());
        sqlAuthorisationDto.setPassword(encodePassword);

        SQLAuthorisation entity = sqlAuthorisationMapper.toEntity(sqlAuthorisationDto);
        return sqlAuthorisationMapper.toDto(sqlAuthorisationRepository.save(entity));
    }

    public List<SQLAuthorisationDto> getAllSQLAuthorisations() {
        return sqlAuthorisationRepository.findAll().stream()
                .map(sqlAuthorisationMapper::toDto)
                .toList();
    }

    /*
    нужна ли тут Dto?
     */
    public void deleteAllSQLAuthorisationByReportId(UUID ReportId) {
        List<SQLAuthorisation> sqlAuthorisations = sqlAuthorisationRepository.findByReportId(ReportId);
        for (SQLAuthorisation sqlAuthorisation : sqlAuthorisations) {
            sqlRequestService.deleteAllRequestsByReportId(sqlAuthorisation.getId());
        }
        sqlAuthorisationRepository.deleteAll(sqlAuthorisations);
    }
}
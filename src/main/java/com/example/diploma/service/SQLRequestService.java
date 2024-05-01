package com.example.diploma.service;

import com.example.diploma.dto.SQLRequestDto;
import com.example.diploma.entity.SQLRequest;
import com.example.diploma.mapper.SQLRequestMapper;
import com.example.diploma.repository.SQLRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SQLRequestService {

    private final SQLRequestMapper sqlRequestMapper;

    private final SQLRequestRepository sqlRequestRepository;

    public SQLRequestDto addSQLRequest(SQLRequestDto sqlRequestDto) {
        SQLRequest entity = sqlRequestMapper.toEntity(sqlRequestDto);
        return sqlRequestMapper.toDto(sqlRequestRepository.save(entity));
    }

    public List<SQLRequestDto> getAllSQLRequests() {
        return sqlRequestRepository.findAll().stream()
                .map(sqlRequestMapper::toDto)
                .toList();
    }
}

package com.example.diploma.service;

import com.example.diploma.entity.SQLRequest;
import com.example.diploma.repository.SQLRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SQLRequestService {

    private final SQLRequestRepository sqlRequestRepository;

    public void deleteAllRequestsByReportId(UUID sqlAuthorisationsId) {
        List<SQLRequest> sqlRequests = sqlRequestRepository.findBySqlAuthorisationsId(sqlAuthorisationsId);
        sqlRequestRepository.deleteAll(sqlRequests);
    }
}

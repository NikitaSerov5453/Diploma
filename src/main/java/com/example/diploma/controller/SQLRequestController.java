package com.example.diploma.controller;

import com.example.diploma.dto.SQLRequestDto;
import com.example.diploma.service.SQLRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
public class SQLRequestController {

    private final SQLRequestService sqlRequestService;

    @PostMapping
    public SQLRequestDto addRequest(@RequestBody SQLRequestDto sqlRequestDto) {
        return sqlRequestService.addSQLRequest(sqlRequestDto);
    }

    @GetMapping
    public List<SQLRequestDto> getAllRequests() {
        return sqlRequestService.getAllSQLRequests();
    }
}

package com.example.diploma.controller;

import com.example.diploma.dto.SQLAuthorisationDto;
import com.example.diploma.service.SQLAuthorisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sqlutorisations")
public class SQLAuthorisationController {

    private final SQLAuthorisationService sqlAuthorisationService;

    @PostMapping
    public SQLAuthorisationDto addSQLAuthorisation(@RequestBody SQLAuthorisationDto sqlAuthorisationDto) {
        return sqlAuthorisationService.addSQLAuthorisation(sqlAuthorisationDto);
    }

    @GetMapping
    public List<SQLAuthorisationDto> getAllSQLAuthorisations() {
        return sqlAuthorisationService.getAllSQLAuthorisations();
    }
}

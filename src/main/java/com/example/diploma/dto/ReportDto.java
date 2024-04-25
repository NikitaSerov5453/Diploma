package com.example.diploma.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDto {

    private UUID id;

    private AutomationDto automation;

    private List<AddresseeDto> addresses;

    private List<SQLRequestDto> sqlRequests;

    private List<SQLAuthorisationDto> sqlAuthorisations;

}

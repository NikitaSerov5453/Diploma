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
public class SQLAuthorisationDto {

    private UUID id;

    private String url;

    private String login;

    private String password;

    private List<SQLRequestDto> sqlRequests;
}

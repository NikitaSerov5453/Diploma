package com.example.diploma.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {

    private UUID id;

    private String name;

    private String lastName;

    private String patronymicName;
}

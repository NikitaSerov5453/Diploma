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
public class UserDto {

    private UUID id;

    private String username;

    private String password;

    private Boolean isLocked;

    private RoleDto role;

    private EmployeeDto employee;
}
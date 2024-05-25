package com.example.diploma.dto;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username;
    private String password;
}

package com.example.diploma.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailConfigurationDto {

    private UUID id;

    private String host;

    private String port;

    private String username;

    private String password;

    private String propertiesAuth;

    private String propertiesStarttlsEnable;
}

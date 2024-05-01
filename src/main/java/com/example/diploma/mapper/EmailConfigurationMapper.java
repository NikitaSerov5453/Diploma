package com.example.diploma.mapper;

import com.example.diploma.dto.EmailConfigurationDto;
import com.example.diploma.entity.EmailConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmailConfigurationMapper {

    EmailConfigurationDto toDto(EmailConfiguration emailConfiguration);

    EmailConfiguration toEntity(EmailConfigurationDto emailConfigurationDto);
}

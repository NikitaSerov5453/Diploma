package com.example.diploma.mapper;

import com.example.diploma.dto.AutomationDto;
import com.example.diploma.entity.automation.Automation;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AutomationMapper {
    AutomationDto toDto(Automation automation);

    Automation toEntity(AutomationDto automationDto);
}

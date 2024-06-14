package com.example.diploma.security.mapper;

import com.example.diploma.dto.ReportDto;
import com.example.diploma.entity.Report;

import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportMapper {

    ReportDto toDto(Report report);

    Report toEntity(ReportDto reportDto);
}

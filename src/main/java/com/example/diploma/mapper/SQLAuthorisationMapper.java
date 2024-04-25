package com.example.diploma.mapper;

import com.example.diploma.dto.SQLAuthorisationDto;
import com.example.diploma.entity.sql.SQLAuthorisation;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SQLAuthorisationMapper {

    SQLAuthorisationDto toDto(SQLAuthorisation sqlAuthorisation);

    SQLAuthorisation toEntity(SQLAuthorisationDto sqlAuthorisationDto);
}

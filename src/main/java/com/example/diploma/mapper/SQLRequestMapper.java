package com.example.diploma.mapper;

import com.example.diploma.dto.SQLRequestDto;
import com.example.diploma.entity.sql.SQLRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SQLRequestMapper {

    SQLRequestDto toDto(SQLRequest sqlRequest);

    SQLRequest toEntity(SQLRequestDto sqlRequestDto);
}

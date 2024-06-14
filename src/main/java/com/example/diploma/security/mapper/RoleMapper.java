package com.example.diploma.security.mapper;

import com.example.diploma.dto.RoleDto;
import com.example.diploma.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    
    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);
}

package com.example.diploma.security.mapper;

import com.example.diploma.dto.EmployeeDto;
import com.example.diploma.entity.Employee;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    EmployeeDto toDto(Employee employee);

    Employee toEntity(EmployeeDto employeeDto);
}

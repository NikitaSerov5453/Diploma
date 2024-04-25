package com.example.diploma.service;

import com.example.diploma.dto.EmployeeDto;
import com.example.diploma.entity.Employee;
import com.example.diploma.mapper.EmployeeMapper;
import com.example.diploma.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee entity = employeeMapper.toEntity(employeeDto);
        return employeeMapper.toDto(employeeRepository.save(entity));
    }

    public List<EmployeeDto> getAllEmployeesDto() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

}

package com.example.diploma.service;

import com.example.diploma.dto.AddresseeDto;
import com.example.diploma.dto.ReportDto;
import com.example.diploma.entity.Report;
import com.example.diploma.entity.email.Addressee;
import com.example.diploma.mapper.AddresseeMapper;
import com.example.diploma.mapper.ReportMapper;
import com.example.diploma.repository.AddresseeRepository;
import com.example.diploma.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddresseeService {
    private final AddresseeRepository addresseeRepository;

    private final AddresseeMapper addresseeMapper;

    public AddresseeDto addAddressee(AddresseeDto addresseeDto) {
        Addressee entity = addresseeMapper.toEntity(addresseeDto);
        return addresseeMapper.toDto(addresseeRepository.save(entity));
    }

    public List<AddresseeDto> getAllAddressees() {
        return addresseeRepository.findAll().stream()
                .map(addresseeMapper::toDto)
                .toList();
    }
}

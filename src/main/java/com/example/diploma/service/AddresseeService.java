package com.example.diploma.service;

import com.example.diploma.dto.AddresseeDto;
import com.example.diploma.entity.Addressee;
import com.example.diploma.mapper.AddresseeMapper;
import com.example.diploma.repository.AddresseeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    /*
    нужна ли тут Dto?
     */
    public void deleteAllAddresseesByReportId(UUID reportId) {
        List<Addressee> addressees = addresseeRepository.findByReportId(reportId);
        addresseeRepository.deleteAll(addressees);
    }

    public List<AddresseeDto> getAllAddresseesByReportId(UUID reportId) {
        return addresseeRepository.findByReportId(reportId).stream().map(addresseeMapper::toDto).toList();
    }

    public void deleteAddressee(UUID id) {
        addresseeRepository.deleteById(id);
    }

    public List<AddresseeDto> getAllAddressees() {
        return addresseeRepository.findAll().stream()
                .map(addresseeMapper::toDto)
                .toList();
    }
}

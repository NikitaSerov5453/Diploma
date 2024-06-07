package com.example.diploma.service;

import com.example.diploma.entity.Addressee;
import com.example.diploma.repository.AddresseeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddresseeService {
    private final AddresseeRepository addresseeRepository;

    public void deleteAllAddresseesByReportId(UUID reportId) {
        List<Addressee> addressees = addresseeRepository.findByReportId(reportId);
        addresseeRepository.deleteAll(addressees);
    }
}

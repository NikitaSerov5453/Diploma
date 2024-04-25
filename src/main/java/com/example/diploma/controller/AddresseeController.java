package com.example.diploma.controller;

import com.example.diploma.dto.AddresseeDto;
import com.example.diploma.dto.ReportDto;
import com.example.diploma.service.AddresseeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/addressees")
public class AddresseeController {

    private final AddresseeService addresseeService;

    @PostMapping
    public AddresseeDto addAddressee(@RequestBody AddresseeDto addresseeDto) {
        return addresseeService.addAddressee(addresseeDto);
    }

    @GetMapping
    public List<AddresseeDto> getAllAddressees() {
        return addresseeService.getAllAddressees();
    }

//    @GetMapping("/{id}")
//    public List<AddresseeDto> getAddresseeByReportId(@PathVariable("id") UUID id) {
//        return addresseeService.getAddresseeByReport(id);
//    }

}

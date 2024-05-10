package com.example.diploma.controller;

import com.example.diploma.dto.AddresseeDto;
import com.example.diploma.service.AddresseeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}

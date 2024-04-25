package com.example.diploma.service;

import com.example.diploma.mapper.EmailConfigurationMapper;
import com.example.diploma.repository.EmailConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailConfigurationService {

    private final EmailConfigurationMapper emailConfigurationMapper;

    private final EmailConfigurationRepository emailConfigurationRepository;


}

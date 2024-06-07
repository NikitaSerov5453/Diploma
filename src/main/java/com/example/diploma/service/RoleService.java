package com.example.diploma.service;

import com.example.diploma.entity.Role;
import com.example.diploma.mapper.RoleMapper;
import com.example.diploma.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}

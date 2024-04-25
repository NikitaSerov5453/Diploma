package com.example.diploma.service;

import com.example.diploma.dto.RoleDto;
import com.example.diploma.entity.Role;
import com.example.diploma.mapper.RoleMapper;
import com.example.diploma.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public void addRole(RoleDto role) {
        roleRepository.save(Role.builder()
                .roleType(role.getRoleType())
                .build());
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .toList();
    }

    public Set<Role> getRoles(List<UUID> roleIds) {
        return roleIds.stream()
                .map(roleRepository::findById)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}

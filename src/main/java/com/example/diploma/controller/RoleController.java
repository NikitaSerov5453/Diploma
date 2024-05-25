package com.example.diploma.controller;

import com.example.diploma.dto.RoleDto;
import com.example.diploma.service.RoleService;
import com.example.diploma.validation.RoleValidator;

import jakarta.validation.ValidationException;

import lombok.RequiredArgsConstructor;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    private final RoleValidator roleValidator;

    @PostMapping
    public void addRole(@RequestBody RoleDto roleDto, BindingResult bindingResult) {
        roleValidator.validate(roleDto, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException("Роль уже присутствует: " + roleDto.getRoleType());
        }

        roleService.addRole(roleDto);
    }

    @GetMapping
    public List<RoleDto> getAllRoles() {
        return roleService.getAllRoles();
    }
}

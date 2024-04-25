package com.example.diploma.validation;

import com.example.diploma.dto.RoleDto;
import com.example.diploma.repository.RoleRepository;
import com.example.diploma.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class RoleValidator implements Validator {

    private final RoleService roleService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RoleDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoleDto roleDto = (RoleDto) target;

        boolean exists = roleService.getAll().stream()
                .anyMatch(role -> roleDto.getRoleType().equals(role.getRoleType().name()));

        if (exists) {
            errors.rejectValue("roleType", null, "Role type already exists");
        }
    }
}

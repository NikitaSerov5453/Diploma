package com.example.diploma.validation;

import com.example.diploma.dto.UserDto;
import com.example.diploma.entity.User;
import com.example.diploma.repository.UserRepository;
import com.example.diploma.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;

        Optional<User> userOptional = userService.getUserByUsername(userDto.getUsername());

        if (userOptional.isPresent()) {
            errors.rejectValue("username", null, "Username already exists");
        }
    }
}

package com.example.diploma.controller;

import com.example.diploma.dto.UserDto;
import com.example.diploma.service.UserService;
import com.example.diploma.validation.UserValidator;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserValidator userValidator;

    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto, BindingResult bindingResult) {
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Имя пользователя уже занято: " + userDto.getUsername());
        }

        return userService.addUser(userDto);
    }

    @GetMapping
    public List<UserDto> getUser() {
        return userService.getAllUsers();
    }
}

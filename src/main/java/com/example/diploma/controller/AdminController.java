package com.example.diploma.controller;

import com.example.diploma.dto.UserDto;
import com.example.diploma.service.UserService;
import com.example.diploma.validation.UserValidator;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Secured("ROLE_ADMIN")
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final UserValidator userValidator;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/ban/{id}")
    public Boolean banUser(@PathVariable UUID id) {
        return userService.banUser(id);
    }

    @GetMapping("/unban/{id}")
    public Boolean unbanUser(@PathVariable UUID id) {
        return userService.unbanUser(id);
    }

    @PostMapping("/add")
    public UserDto addUser(@RequestBody UserDto userDto, BindingResult bindingResult) {
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Имя пользователя уже занято: " + userDto.getUsername());
        }

        return userService.addUser(userDto);
    }

    @PutMapping("/update")
    public UserDto updateUser(@RequestBody UserDto userDto, BindingResult bindingResult) {
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Имя пользователя уже занято: " + userDto.getUsername());
        }

        return userService.updateUser(userDto);
    }

}

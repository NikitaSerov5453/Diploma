package com.example.diploma.controller;

import com.example.diploma.dto.UserDto;
import com.example.diploma.dto.view.UserView;
import com.example.diploma.entity.RefreshToken;
import com.example.diploma.entity.User;
import com.example.diploma.service.RefreshTokenService;
import com.example.diploma.service.UserService;
import com.example.diploma.validation.UserValidator;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Secured("ROLE_ADMIN")
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final UserValidator userValidator;

    private final RefreshTokenService refreshTokenService;

    @GetMapping
    public List<UserView> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/user/{id}")
    public Optional<UserView> getUserById(@PathVariable UUID id) {
        return userService.findUserById(id);
    }

    @PutMapping("/ban/{id}")
    public Boolean banUser(@PathVariable UUID id) {
        List<RefreshToken> refreshTokens = refreshTokenService.findByUserId(id);
        refreshTokens.forEach(refreshTokenService::deleteRefreshToken);
        return userService.banUnbanUser(id);
    }

    @PostMapping("/add")
    public UserDto addUser(@RequestBody UserDto userDto, BindingResult bindingResult) {
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Имя пользователя уже занято: " + userDto.getUsername());
        }

        return userService.addUser(userDto);
    }

    @PutMapping("/update/{id}")
    public UserDto updateUser(@PathVariable UUID id, @RequestBody UserDto userDto, BindingResult bindingResult) {
        Optional<User> userEntity = userService.getUserById(id);

        if (!userEntity.get().getUsername().equals(userDto.getUsername())) {
            userValidator.validate(userDto, bindingResult);
            if (bindingResult.hasErrors()) {
                throw new ValidationException("Имя пользователя уже занято: " + userDto.getUsername());
            }
        }

        return userService.updateUser(userEntity.get(), userDto);
    }
}

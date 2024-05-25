package com.example.diploma.controller;

import com.example.diploma.dto.UserDto;
import com.example.diploma.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
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

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/current")
    public UserDto getCurrent() {
        return userService.getCurrentUser();
    }

    @GetMapping("/ban/{id}")
    public Boolean banUser(@PathVariable UUID id) {
        return userService.banUser(id);
    }
}

package com.example.diploma.controller;

import com.example.diploma.dto.jwt.JwtRequestDto;
import com.example.diploma.dto.jwt.RefreshTokenRequestDto;
import com.example.diploma.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/login")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }

    @PostMapping
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto requestDto) {
        return authService.createAuthToken(requestDto);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> updateAuthToken(@RequestBody RefreshTokenRequestDto requestDto) {
        return authService.refreshAuthToken(requestDto);
    }
}

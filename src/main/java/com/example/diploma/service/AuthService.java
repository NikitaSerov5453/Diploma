package com.example.diploma.service;

import com.example.diploma.dto.jwt.JwtRequestDto;
import com.example.diploma.dto.jwt.JwtResponseDto;
import com.example.diploma.dto.jwt.RefreshTokenRequestDto;
import com.example.diploma.entity.RefreshToken;
import com.example.diploma.exception.AppError;
import com.example.diploma.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final JwtTokenUtils jwtTokenUtils;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
        return ResponseEntity.ok(JwtResponseDto.builder()
                .refreshToken(refreshToken.getToken())
                .token(token)
                .build());

    }

    public ResponseEntity<?> refreshAuthToken(@RequestBody RefreshTokenRequestDto refreshTokenRequest) {
        UserDetails userDetails = userService.loadUserByUsername(refreshTokenRequest.getUserName());
        return refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String authToken = jwtTokenUtils.generateToken(userDetails);
                    return ResponseEntity.ok(JwtResponseDto.builder()
                            .token(authToken)
                            .refreshToken(refreshTokenRequest.getRefreshToken())
                            .build());
                }).orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }


}

package com.example.diploma.utils;

import com.example.diploma.entity.RefreshToken;
import com.example.diploma.repository.RefreshTokenRepository;
import com.example.diploma.repository.UserRepository;
import com.example.diploma.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenUtils {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserService userService;

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userService.getUserByUsername(username).get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(3600 * 12))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + "Срок действия Refresh токена истек");
        }
        return token;
    }

}

package com.example.diploma.dto.jwt;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenRequestDto {

    private String userName;

    private String refreshToken;
}

package com.example.trendtyschool.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class TokenResponse {
    private String accessToken;
    private String refreshToken;

}
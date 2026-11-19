package org.sopt.auth.controller.dto;

import org.sopt.auth.service.vo.AuthTokens;

public record LoginResponse(String accessToken) {
    public static LoginResponse from(AuthTokens tokens) {
        return new LoginResponse(tokens.accessToken());
    }
}
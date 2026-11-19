package org.sopt.auth.controller.dto;

import org.sopt.auth.service.vo.AuthTokens;

public record TokenReissueResponse(
        String accessToken
) {

    public static TokenReissueResponse from(AuthTokens tokens) {
        return new TokenReissueResponse(tokens.accessToken());
    }
}

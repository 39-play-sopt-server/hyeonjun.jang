package org.sopt.security;

import org.springframework.http.ResponseCookie;

public class RefreshTokenCookie {

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    public static ResponseCookie create(String refreshToken, long expiresInSeconds, boolean secure) {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .path("/")
                .httpOnly(true)
                .secure(secure)
                .sameSite("Lax")
                .maxAge(expiresInSeconds)
                .build();
    }

    public static ResponseCookie delete(boolean secure) {
        return create("", 0, secure);
    }
}

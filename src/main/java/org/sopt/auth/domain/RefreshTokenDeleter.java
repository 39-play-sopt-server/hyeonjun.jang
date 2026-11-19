package org.sopt.auth.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenDeleter {

    private final RefreshTokenRepository refreshTokenRepository;

    public void delete(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}
package org.sopt.auth.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.sopt.Member.code.MemberErrorCode;
import org.sopt.Member.domain.Member;
import org.sopt.Member.domain.MemberAppender;
import org.sopt.Member.domain.MemberDeleter;
import org.sopt.Member.domain.MemberReader;
import org.sopt.Member.enums.Gender;
import org.sopt.Member.enums.Role;
import org.sopt.auth.code.AuthErrorCode;
import org.sopt.auth.domain.RefreshToken;
import org.sopt.auth.domain.RefreshTokenDeleter;
import org.sopt.auth.domain.RefreshTokenReader;
import org.sopt.auth.domain.RefreshTokenRotator;
import org.sopt.auth.service.vo.AuthTokens;
import org.sopt.common.exception.BusinessException;
import org.sopt.security.blacklist.BlacklistHandler;

import org.sopt.security.jwt.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberReader memberReader;
    private final MemberAppender memberAppender;
    private final MemberDeleter memberDeleter;
    private final JwtProvider jwtProvider;

    private final RefreshTokenRotator refreshTokenRotator;
    private final RefreshTokenReader refreshTokenReader;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenDeleter refreshTokenDeleter;
    private final BlacklistHandler blacklistHandler;

    @Transactional
    public AuthTokens login(final String email, final String password) {
        Member member = loginWithCredentials(email, password);

        return issueTokens(member);
    }

    @Transactional
    public void signup(String email, String password, String name, String bio, Gender gender, Role role) {
        if (memberReader.existsByEmail(email)) {
            throw new BusinessException(MemberErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String encodedPassword =
                passwordEncoder.encode(password);

        memberAppender.append(
                email,
                encodedPassword,
                name,
                bio,
                gender,
                // 서버에서 역할을 받으면 안되지만 과제라서 허용했습니다~
                role
        );
    }

    @Transactional
    public void logout(final Long userId, final String accessToken) {
        refreshTokenDeleter.delete(userId);
        blacklistHandler.add(accessToken);
    }

    @Transactional
    public void withdraw(final Long userId, final String accessToken) {
        refreshTokenDeleter.delete(userId);
        memberDeleter.delete(userId);
        blacklistHandler.add(accessToken);
    }

    @Transactional
    public AuthTokens loginWithOAuth2(final Member member) {
        return issueTokens(member);
    }

    @Transactional
    public AuthTokens reissueTokens(
            final String refreshTokenValue
    ) {
        Claims claims =
                jwtProvider.verifyRefreshToken(refreshTokenValue);

        Long tokenMemberId =
                Long.valueOf(claims.getSubject());

        RefreshToken storedRefreshToken =
                refreshTokenReader.read(refreshTokenValue);

        if (!storedRefreshToken.getUserId().equals(tokenMemberId)) {
            throw new BusinessException(
                    AuthErrorCode.INVALID_REFRESH_TOKEN
            );
        }

        Member member = memberReader.read(tokenMemberId);

        return issueTokens(member);
    }

    private AuthTokens issueTokens(final Member member) {

        String accessToken = jwtProvider.createAccessToken(
                member.getId(),
                member.getEmail(),
                member.getRole()
        );
        String refreshToken = jwtProvider.createRefreshToken(member.getId());

        refreshTokenRotator.rotate(member.getId(), refreshToken);

        return AuthTokens.of(accessToken, refreshToken);
    }

    private Member loginWithCredentials(final String email, final String password) {
        Member member = memberReader.readByEmail(email);

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new BusinessException(AuthErrorCode.PASSWORD_MISMATCH);
        }

        return member;
    }
}

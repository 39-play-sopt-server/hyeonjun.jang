package org.sopt.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.sopt.Member.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private static final String TOKEN_TYPE = "tokenType";
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String REFRESH_TOKEN = "REFRESH";

    private final SecretKey secretKey;
    private final long accessTokenExpiresInSeconds;
    private final long refreshTokenExpiresInSeconds;

    public JwtProvider(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.access-token-expires-in-seconds}") long accessTokenExpiresInSeconds,
            @Value("${security.jwt.refresh-token-expires-in-seconds}") long refreshTokenExpiresInSeconds
    ) {
        this.secretKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );
        this.accessTokenExpiresInSeconds = accessTokenExpiresInSeconds;
        this.refreshTokenExpiresInSeconds = refreshTokenExpiresInSeconds;
    }

    public String createAccessToken(Long memberId, String email, Role role) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(String.valueOf(memberId))   // 누구의 토큰인지
                .claim("email", email)
                .claim("role", role.name())
                .claim(TOKEN_TYPE, ACCESS_TOKEN)
                .issuedAt(Date.from(now))                       // 발급 시각 (Instant로 변경한 이유는 아래 초를 더하려고)
                .expiration(Date.from(now.plusSeconds(accessTokenExpiresInSeconds))) // 만료 시각
                .signWith(secretKey)                 // 서명
                .compact();
    }

    public String createRefreshToken(Long memberId) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(String.valueOf(memberId)) // 누구의 토큰인지
                .claim(TOKEN_TYPE, REFRESH_TOKEN) // 당연히 리프레쉬 토큰
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(
                        refreshTokenExpiresInSeconds
                )))
                .signWith(secretKey)
                .compact();


    }

    public Claims verifyAccessToken(String token) {
        Claims claims = parseToken(token);
        validateTokenType(claims, ACCESS_TOKEN);

        return claims;
    }

    public Claims verifyRefreshToken(String token) {
        Claims claims = parseToken(token);
        validateTokenType(claims, REFRESH_TOKEN);

        return claims;
    }

    private Claims parseToken(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("토큰이 없습니다.");
        }

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private void validateTokenType(
            Claims claims,
            String expectedTokenType
    ) {
        String actualTokenType = claims.get(
                TOKEN_TYPE,
                String.class
        );

        if (!expectedTokenType.equals(actualTokenType)) {
            throw new JwtException("잘못된 종류의 토큰입니다.");
        }
    }
}
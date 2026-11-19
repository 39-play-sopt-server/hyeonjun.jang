package org.sopt.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.sopt.security.MemberAuthentication;
import org.sopt.security.blacklist.BlacklistHandler;
import org.sopt.security.oauth2.provider.PrincipalProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.sopt.Member.enums.Role;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final BlacklistHandler blacklistHandler;
    private final AccessTokenExtractor accessTokenExtractor;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = accessTokenExtractor.extract(request);

        // 토큰이 없거나 로그아웃된 토큰이면 인증하지 않고 넘긴다.
        if (token == null || blacklistHandler.isBlacklisted(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Claims claims = jwtProvider.verifyAccessToken(token);
            Long memberId = Long.valueOf(claims.getSubject());

            String roleClaim = claims.get("role", String.class);
            if (roleClaim == null) {
                throw new JwtException("권한 정보가 없는 Access Token입니다.");
            }

            Role role = Role.valueOf(roleClaim);

            PrincipalProvider principal = PrincipalProvider.of(memberId);
            MemberAuthentication authentication =
                    MemberAuthentication.create(principal, role);

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        } catch (JwtException | IllegalArgumentException exception) {
            // 잘못되거나 만료된 토큰은 인증하지 않는다.
            // 보호된 API라면 SecurityConfig가 최종적으로 401을 반환한다.
        }

        filterChain.doFilter(request, response);
    }
}

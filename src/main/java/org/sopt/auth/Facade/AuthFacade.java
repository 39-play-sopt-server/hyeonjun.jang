package org.sopt.auth.Facade;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.sopt.Member.enums.Gender;
import org.sopt.Member.enums.Role;
import org.sopt.auth.service.AuthService;
import org.sopt.auth.service.vo.AuthTokens;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.sopt.security.jwt.AccessTokenExtractor;
import org.sopt.security.oauth2.provider.PrincipalProvider;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthFacade {

    private final AuthService authService;
    private final AccessTokenExtractor accessTokenExtractor;

    @Transactional
    public AuthTokens login(final String email, final String password) {
        return authService.login(email, password);
    }

    @Transactional
    public void signup(
            String email,
            String password,
            String name,
            String bio,
            Gender gender,
            Role role
    ) {
        authService.signup(email, password, name, bio, gender, role);
    }

    @Transactional
    public AuthTokens reissue(final String refreshToken) {
        return authService.reissueTokens(refreshToken);
    }

    @Transactional
    public void logout(PrincipalProvider provider, HttpServletRequest httpRequest) {
        String token = accessTokenExtractor.extract(httpRequest);
        authService.logout(provider.memberId(), token);
    }

    @Transactional
    public void withdraw(PrincipalProvider provider, HttpServletRequest httpRequest) {
        String token = accessTokenExtractor.extract(httpRequest);
        authService.withdraw(provider.memberId(), token);
    }
}

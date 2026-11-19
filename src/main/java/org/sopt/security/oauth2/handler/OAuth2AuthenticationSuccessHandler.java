package org.sopt.security.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.sopt.Member.domain.Member;
import org.sopt.Member.service.MemberService;
import org.sopt.auth.service.AuthService;
import org.sopt.auth.service.vo.AuthTokens;
import org.sopt.security.RefreshTokenCookie;
import org.sopt.security.oauth2.OAuth2Authentication;
import org.sopt.security.oauth2.memberInfo.OAuth2MemberInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberService memberService;
    private final AuthService authService;

    @Value("${client.oauth2-redirect-uri}")
    private String clientRedirectUri;
    @Value("${security.jwt.refresh-token-expires-in-seconds:1209600}")
    private long refreshTokenExpiresInSeconds;
    @Value("${security.cookie.secure:false}")
    private boolean secureCookie;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        OAuth2Authentication principal = (OAuth2Authentication) authentication.getPrincipal();
        OAuth2MemberInfo memberInfo = principal.getMemberInfo();

        Member member = memberService.findOrCreateByOAuth2(memberInfo);
        AuthTokens tokens = authService.loginWithOAuth2(member);

        ResponseCookie cookie = RefreshTokenCookie.create(
                tokens.refreshToken(),
                refreshTokenExpiresInSeconds,
                secureCookie
        );
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // Access Token은 URL에 노출하지 않고, 프론트에서 Refresh Token 쿠키로 재발급한다.
        getRedirectStrategy().sendRedirect(request, response, clientRedirectUri);
    }
}

package org.sopt.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.Member.code.MemberSuccessCode;
import org.sopt.Member.controller.dto.CreateMemberRequest;
import org.sopt.auth.Facade.AuthFacade;
import org.sopt.auth.code.AuthSuccessCode;
import org.sopt.auth.controller.dto.TokenReissueResponse;
import org.sopt.auth.controller.dto.request.LoginRequest;
import org.sopt.auth.controller.dto.LoginResponse;
import org.sopt.auth.service.vo.AuthTokens;
import org.sopt.common.dto.CommonResponse;
import org.sopt.security.RefreshTokenCookie;
import org.sopt.security.oauth2.provider.PrincipalProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController implements AuthApi {

    private final AuthFacade authFacade;

    @Value("${security.jwt.refresh-token-expires-in-seconds:1209600}")
    private long refreshTokenExpiresInSeconds;
    @Value("${security.cookie.secure:false}")
    private boolean secureCookie;

    @PostMapping("/signup")
    public CommonResponse<Void> signup(
            @Valid @RequestBody CreateMemberRequest request
    ) {
        authFacade.signup(
                request.email(),
                request.password(),
                request.name(),
                request.bio(),
                request.gender(),
                request.role()
        );
        return CommonResponse.success(MemberSuccessCode.MEMBER_CREATED);
    }

    @PostMapping("/login")
    public CommonResponse<LoginResponse> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        AuthTokens tokens = authFacade.login(request.email(), request.password());
        setRefreshTokenCookie(response, tokens.refreshToken());
        return CommonResponse.success(AuthSuccessCode.LOGIN_SUCCEED, LoginResponse.from(tokens));
    }

    @PostMapping("/reissue")
    public CommonResponse<TokenReissueResponse> reissue(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response
    ) {
        AuthTokens tokens = authFacade.reissue(refreshToken);
        setRefreshTokenCookie(response, tokens.refreshToken());
        return CommonResponse.success(AuthSuccessCode.TOKEN_REISSUED, TokenReissueResponse.from(tokens));
    }

    @PostMapping("/logout")
    public CommonResponse<Void> logout(
            @AuthenticationPrincipal PrincipalProvider provider,
            HttpServletRequest httpRequest,
            HttpServletResponse response
    ) {
        authFacade.logout(provider, httpRequest);
        clearRefreshTokenCookie(response);
        return CommonResponse.success(AuthSuccessCode.LOGOUT_SUCCEED);
    }

    @DeleteMapping("/me")
    public CommonResponse<Void> withdraw(
            @AuthenticationPrincipal PrincipalProvider provider,
            HttpServletRequest httpRequest,
            HttpServletResponse response
    ) {
        authFacade.withdraw(provider, httpRequest);
        clearRefreshTokenCookie(response);
        return CommonResponse.success(MemberSuccessCode.MEMBER_DELETED);
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = RefreshTokenCookie.create(
                refreshToken,
                refreshTokenExpiresInSeconds,
                secureCookie
        );
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private void clearRefreshTokenCookie(HttpServletResponse response) {
        response.addHeader(
                HttpHeaders.SET_COOKIE,
                RefreshTokenCookie.delete(secureCookie).toString()
        );
    }
}

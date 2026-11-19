package org.sopt.common.config;

import org.sopt.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.sopt.security.oauth2.OAuth2AuthenticationService;
import org.sopt.security.oauth2.handler.OAuth2AuthenticationFailureHandler;
import org.sopt.security.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           OAuth2AuthenticationService oAuth2AuthenticationService,
                                           OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
                                           OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler)
            throws Exception {
        return http
                // CSRF 비활성화, Authorization 헤더로 보내면 CSRF의 전제 조건이 없기 때문
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                // 폼 로그인, HTTP Basic 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // 세션 STATELESS
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // URL별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 1. 로그인, 회원가입, Swagger, OAuth2는 비회원도 접근 가능
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v1/auth/login",
                                "/v1/auth/signup",
                                "/v1/auth/reissue",
                                "/oauth2/authorization/**",
                                "/login/oauth2/code/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/v1/auth/logout"
                        ).authenticated()

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/v1/auth/me"
                        ).authenticated()

                        // 2. 게시글 목록, 상세, 검색은 비회원도 가능
                        // GET 요청만 공개되므로 작성·수정·삭제는 공개되지 않는다.
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/posts",
                                "/api/v1/posts/**"
                        ).permitAll()

                        // 3. 내 정보 확인은 로그인한 회원이면 가능
                        // 반드시 /member/** 관리자 규칙보다 먼저 작성해야 한다.
                        .requestMatchers(
                                HttpMethod.GET,
                                "/member/me"
                        ).authenticated()

                        // 4. 게시글 삭제는 ADMIN만 가능
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/posts/**"
                        ).hasRole("ADMIN")

                        // 5. 회원 목록·단건 조회·삭제는 ADMIN만 가능
                        .requestMatchers(
                                "/member",
                                "/member/**"
                        ).hasRole("ADMIN")

                        // 6. 게시글 작성은 로그인한 회원
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/posts"
                        ).authenticated()

                        // 7. 좋아요는 로그인한 회원
                        // 현재 주소가 /{postId}/like/{memberId}이므로 이렇게 작성
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/posts/*/like/*"
                        ).authenticated()

                        // 8. 게시글 수정은 로그인한 회원
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/v1/posts/*"
                        ).authenticated()

                        // 위에서 명시하지 않은 요청은 전부 차단
                        .anyRequest().denyAll()
                )
                // 인증 실패 시 401 응답
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                // JWT 인증 필터 추가
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(oAuth2AuthenticationService))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler)
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

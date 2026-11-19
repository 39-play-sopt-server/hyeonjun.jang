package org.sopt.Member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sopt.Member.enums.Gender;
import org.sopt.Member.enums.Role;
import org.sopt.security.oauth2.provider.OAuth2Provider;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    private final Long id;
    private final String email;
    private final String password;

    private String name;
    private String bio;

    private final Gender gender;
    private final Role role;

    private final OAuth2Provider oAuth2Provider;
    private final String oAuth2Id;

    // DB 데이터를 도메인 객체로 복원할 때 사용
    public static Member createWithId(
            Long id,
            String email,
            String password,
            String name,
            String bio,
            Gender gender,
            Role role,
            OAuth2Provider oAuth2Provider,
            String oAuth2Id
    ) {
        return new Member(
                id,
                email,
                password,
                name,
                bio,
                gender,
                role,
                oAuth2Provider,
                oAuth2Id
        );
    }

    // 이메일/비밀번호 기반 회원가입
    public static Member create(
            String email,
            String name,
            String password,
            String bio,
            Gender gender,
            Role role
    ) {
        return new Member(
                null,
                email,
                password,
                name,
                bio,
                gender,
                role,
                null,
                null
        );
    }

    // OAuth2 기반 회원가입
    public static Member createWithOAuth2(
            String email,
            String name,
            OAuth2Provider provider,
            String socialId
    ) {
        return new Member(
                null,
                email,
                null,
                name,
                null,
                null,
                Role.USER,
                provider,
                socialId
        );
    }

    public void update(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }
}

package org.sopt.security.oauth2.provider;

public record PrincipalProvider(
        Long memberId
) {

    public static PrincipalProvider of(final Long memberId) {
        return new PrincipalProvider(memberId);
    }
}
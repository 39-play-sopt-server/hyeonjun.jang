package org.sopt.security;

import java.util.Collections;
import org.sopt.Member.enums.Role;
import org.sopt.security.oauth2.provider.PrincipalProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class MemberAuthentication extends UsernamePasswordAuthenticationToken {

    private MemberAuthentication(
            PrincipalProvider provider,
            Role role
    ) {
        super(
                provider,
                null,
                Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + role.name())
                )
        );
    }

    public static MemberAuthentication create(
            PrincipalProvider provider,
            Role role
    ) {
        return new MemberAuthentication(provider, role);
    }
}
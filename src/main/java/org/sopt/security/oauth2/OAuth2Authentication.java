package org.sopt.security.oauth2;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.security.oauth2.memberInfo.OAuth2MemberInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@RequiredArgsConstructor(staticName = "create")
public class OAuth2Authentication implements OAuth2User {

    private final OAuth2MemberInfo memberInfo;

    @Override
    public String getName() {
        return memberInfo.getUsername();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return memberInfo.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
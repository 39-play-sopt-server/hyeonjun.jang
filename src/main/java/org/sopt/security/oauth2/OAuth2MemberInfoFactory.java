package org.sopt.security.oauth2;

import java.util.Map;
import org.sopt.security.oauth2.memberInfo.GoogleOAuth2MemberInfo;
import org.sopt.security.oauth2.memberInfo.OAuth2MemberInfo;
import org.sopt.security.oauth2.provider.OAuth2Provider;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

public class OAuth2MemberInfoFactory {
    public static OAuth2MemberInfo create(OAuth2Provider provider, Map<String, Object> attributes) {
        return switch (provider) {
            case GOOGLE -> GoogleOAuth2MemberInfo.from(attributes);
            default -> throw new OAuth2AuthenticationException("Unsupported provider: " + provider);
        };
    }
}

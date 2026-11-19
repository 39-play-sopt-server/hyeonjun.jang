package org.sopt.security.oauth2.memberInfo;


import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.security.oauth2.provider.OAuth2Provider;

@Getter
@RequiredArgsConstructor(staticName = "from")
public class GoogleOAuth2MemberInfo implements OAuth2MemberInfo {

    private final Map<String, Object> attributes;

    @Override
    public String getEmail() {
        return (String) attributes.getOrDefault("email", null);
    }

    @Override
    public String getUsername() {
        return (String) attributes.getOrDefault("name", null);
    }

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.GOOGLE;
    }

    @Override
    public String getSocialId() {
        return Optional.ofNullable(attributes.get("sub"))
                .orElseThrow(() -> new IllegalStateException("sub attribute is missing"))
                .toString();
    }
}
package org.sopt.security.oauth2.memberInfo;

import java.util.Map;
import org.sopt.security.oauth2.provider.OAuth2Provider;

public interface OAuth2MemberInfo {
    String getEmail();

    String getUsername();

    Map<String, Object> getAttributes();

    OAuth2Provider getProvider();

    String getSocialId();
}

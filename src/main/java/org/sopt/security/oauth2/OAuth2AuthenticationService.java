package org.sopt.security.oauth2;

import java.util.Map;
import org.sopt.security.oauth2.memberInfo.OAuth2MemberInfo;
import org.sopt.security.oauth2.provider.OAuth2Provider;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Service
public class OAuth2AuthenticationService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest memberRequest) throws OAuth2AuthenticationException {
        if (memberRequest == null) {
            throw new OAuth2AuthenticationException("userRequest is null");
        }

        OAuth2User oAuth2Member = super.loadUser(memberRequest);
        String registrationId = memberRequest.getClientRegistration().getRegistrationId();
        OAuth2Provider provider = OAuth2Provider.from(registrationId);

        if (provider == OAuth2Provider.UNKNOWN) {
            throw new OAuth2AuthenticationException("Unknown provider: " + registrationId);
        }

        Map<String, Object> attributes = oAuth2Member.getAttributes();
        OAuth2MemberInfo memberInfo = OAuth2MemberInfoFactory.create(provider, attributes);

        return OAuth2Authentication.create(memberInfo);
    }
}

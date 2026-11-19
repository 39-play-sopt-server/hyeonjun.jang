package org.sopt.Member.domain;

import lombok.RequiredArgsConstructor;
import org.sopt.Member.enums.Gender;
import org.sopt.Member.enums.Role;
import org.sopt.security.oauth2.memberInfo.OAuth2MemberInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberAppender {
    private final MemberRepository memberRepository;

    @Transactional
    public Member append(
            String email,
            String password,
            String name,
            String bio,
            Gender gender,
            Role role
    ) {
        Member member = Member.create(
                email, name, password, bio, gender, role);

        return memberRepository.save(member);
    }

    public Member append(OAuth2MemberInfo memberInfo) {
        Member newMember = Member.createWithOAuth2(
                memberInfo.getEmail(),
                memberInfo.getUsername(),
                memberInfo.getProvider(),
                memberInfo.getSocialId()
        );
        return memberRepository.save(newMember);
    }
}

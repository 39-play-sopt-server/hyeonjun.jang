package org.sopt.Member.domain;

import org.sopt.Member.enums.Gender;
import org.sopt.Member.enums.Role;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MemberAppender {
    private final MemberRepository memberRepository;

    public MemberAppender(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member append(
            String email,
            String name,
            String bio,
            Gender gender,
            Role role
    ) {
        Member member = Member.create(
                email, name, bio, gender, role);

        return memberRepository.save(member);
    }
}

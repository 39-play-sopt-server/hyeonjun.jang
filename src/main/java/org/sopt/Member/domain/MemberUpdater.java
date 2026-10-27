package org.sopt.Member.domain;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class MemberUpdater {
    private final MemberRepository memberRepository;
    private final MemberReader memberReader;

    public MemberUpdater(MemberRepository memberRepository, MemberReader memberReader) {
        this.memberRepository = memberRepository;
        this.memberReader = memberReader;
    }

    @Transactional
    public void update(Long id, String name, String bio) {
        Member member = memberReader.read(id);
        member.update(name, bio);
        memberRepository.save(member);
    }
}

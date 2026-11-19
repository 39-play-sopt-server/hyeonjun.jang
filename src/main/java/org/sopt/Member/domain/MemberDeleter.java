package org.sopt.Member.domain;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class MemberDeleter {
    private final MemberRepository memberRepository;
    private final MemberReader memberReader;

    public MemberDeleter(MemberRepository memberRepository, MemberReader memberReader) {
        this.memberRepository = memberRepository;
        this.memberReader = memberReader;
    }

    @Transactional
    public void delete(Long id) {
        memberReader.read(id);
        memberRepository.delteById((id));
    }


}

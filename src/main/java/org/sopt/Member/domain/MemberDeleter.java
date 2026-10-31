package org.sopt.Member.domain;

import jakarta.transaction.Transactional;
import java.beans.Transient;
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
        Member member = memberReader.read(id);
        memberRepository.delteById((id));
    }


}

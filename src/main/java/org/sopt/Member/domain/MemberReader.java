package org.sopt.Member.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sopt.Member.code.MemberErrorCode;
import org.sopt.common.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class MemberReader {
    private final MemberRepository memberRepository;

    public MemberReader(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public Member read(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new BusinessException(MemberErrorCode.USER_NOT_FOUND));
    }

    public List<Member> readAll() {
        return memberRepository.findAll();
    }

    public Map<Long, Member> readAllByIds(Set<Long> ids) {
        return memberRepository.findAllByIdsAsMap(ids);
    }
}

package org.sopt.Member.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public Optional<Member> find(Long id) {
        return memberRepository.findById(id);
    }

    public Member readByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new BusinessException(MemberErrorCode.USER_NOT_FOUND));
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public List<Member> readAll() {
        return memberRepository.findAll();
    }

    public Map<Long, Member> readAllByIds(Set<Long> ids) {
        return memberRepository.findAllByIdsAsMap(ids);
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

}

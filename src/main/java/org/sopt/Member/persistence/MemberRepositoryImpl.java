package org.sopt.Member.persistence;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.sopt.Member.domain.Member;
import org.sopt.Member.domain.MemberRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJpaRepository memberJpaRepository;

    public MemberRepositoryImpl(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(MemberJpaEntity.fromDomain(member)).toDomain();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberJpaRepository.findById(id).map(MemberJpaEntity::toDomain);
    }

    @Override
    public List<Member> findAll() {
        return memberJpaRepository.findAll().stream().map(MemberJpaEntity::toDomain).toList();
    }

    @Override
    public Map<Long, Member> findAllByIdsAsMap(Set<Long> ids) {
        return memberJpaRepository.findAllById(ids)
                .stream()
                .collect(
                        Collectors.toUnmodifiableMap(
                                MemberJpaEntity::getId,
                                MemberJpaEntity::toDomain
                        )
                );
    }

    @Override
    public void delteById(Long id) {
        memberJpaRepository.deleteById(id);
    }
}

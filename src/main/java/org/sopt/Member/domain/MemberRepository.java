package org.sopt.Member.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long id);

    List<Member> findAll();
    //User에게는 순서가 중요하지 않는다.
    Map<Long, Member> findAllByIdsAsMap(Set<Long> ids);


    void delteById(Long id);
}

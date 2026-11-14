package org.sopt.Member.persistence;

import java.util.Optional;
import org.sopt.Member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {
    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}

package org.sopt.Member.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {
}

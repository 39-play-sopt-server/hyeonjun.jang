package org.sopt.like.persistence;


import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeJpaRepository extends JpaRepository<LikeJpaEntity, Long>, LikeJpaRepositoryCustom  {

    boolean existsByPostIdAndMemberId(Long postId, Long memberId);

    void deleteByPostIdAndMemberId(Long postId, Long memberId);

    Long countByPostId(Long postId);

}

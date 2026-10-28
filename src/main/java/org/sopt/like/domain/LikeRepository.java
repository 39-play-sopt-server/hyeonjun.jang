package org.sopt.like.domain;

import java.util.Map;
import java.util.Set;

public interface LikeRepository {
    Like save(Like like);

    boolean existsByPostIdAndMemberId(Long postId, Long memberId);

    void deleteByPostIdAndMemberId(Long postId, Long memberId);

    Long countByPostId(Long postId);

    Map<Long, Long> countByPostIds(Set<Long> Ids);
}

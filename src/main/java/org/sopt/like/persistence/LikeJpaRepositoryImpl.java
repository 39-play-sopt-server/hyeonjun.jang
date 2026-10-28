package org.sopt.like.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LikeJpaRepositoryImpl implements LikeJpaRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public LikeJpaRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Map<Long, Long> countByPostIds(Set<Long> postIds) {
        if (postIds.isEmpty()) {
            return Map.of();
        }

        QLikeJpaEntity like = QLikeJpaEntity.likeJpaEntity;

        // 좋아요 수를 게시글마다 개별 조회하지 않고, postIds를 기준으로 groupBy해서 한 번에 Map으로 가져오는 구조
        return queryFactory
                .select(like.postId, like.count())
                .from(like)
                .where(like.postId.in(postIds))
                .groupBy(like.postId)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(like.postId),
                        tuple -> tuple.get(like.count())
                ));
    }
}

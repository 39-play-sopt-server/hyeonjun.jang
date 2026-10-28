package org.sopt.post.persistence;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.sopt.post.enums.PostCategory;
import org.sopt.post.service.vo.PostSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

public class PostJpaRepositoryCustomImpl implements PostJpaRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QPostJpaEntity qPost = QPostJpaEntity.postJpaEntity;

    public PostJpaRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<PostJpaEntity> search(PostSearchCondition condition, Pageable pageable) {
        List<PostJpaEntity> queryResults = queryFactory
                .selectFrom(qPost)
                .where(
                        titleContains(condition.keyword()),
                        authorIdEq(condition.authorId()),
                        categoryEq(condition.category())
                )
                .orderBy(qPost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory.select(qPost.count())
                .from(qPost)
                .where(
                        titleContains(condition.keyword()),
                        authorIdEq(condition.authorId()),
                        categoryEq(condition.category())
                )
                .fetchOne();

        return PageableExecutionUtils.getPage(queryResults, pageable, () -> (count != null ? count : 0L));
    }

    private BooleanExpression titleContains(String keyword) {
        return keyword == null || keyword.isBlank()
                ? null
                : qPost.title.containsIgnoreCase(keyword);
    }

    private BooleanExpression authorIdEq(Long authorId) {
        return authorId == null ? null : qPost.authorId.eq(authorId);
    }

    private BooleanExpression categoryEq(PostCategory category) {
        return category == null ? null : qPost.postCategory.eq(category);
    }
}

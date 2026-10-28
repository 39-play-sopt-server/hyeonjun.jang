package org.sopt.post.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.sopt.post.service.vo.PostSearchCondition;

public interface PostJpaRepositoryCustom {
    Page<PostJpaEntity> search(PostSearchCondition condition, Pageable pageable);
}

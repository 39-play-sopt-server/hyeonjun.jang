package org.sopt.post.domain;

import java.util.Optional;
import org.sopt.common.dto.PageOffset;
import org.sopt.post.enums.PostCategory;
import org.sopt.post.service.vo.PostSearchCondition;
import org.springframework.data.domain.Page;

public interface PostRepository {
    Optional<Post> findById(Long id);

    Page<Post> findAll(PageOffset pageOffset);

    Page<Post> findAllByPostCategory(PostCategory postCategory, PageOffset pageOffset);

    Page<Post> search(PostSearchCondition condition, PageOffset pageOffset);

    Post save(Post post);

    void delete(Post post);
}

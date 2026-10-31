package org.sopt.post.domain;

import org.sopt.common.dto.PageOffset;
import org.sopt.post.enums.PostCategory;
import org.sopt.post.service.vo.PostSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PostFinder {
    private final PostRepository postRepository;

    public PostFinder(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<Post> find(PostCategory postCategory, PageOffset pageOffset) {
        if (postCategory != null) {
            return postRepository.findAllByPostCategory(postCategory, pageOffset);
        }
        return postRepository.findAll(pageOffset);
    }

    public Page<Post> search(PostSearchCondition condition, PageOffset pageOffset) {
        return postRepository.search(condition, pageOffset);
    }
}

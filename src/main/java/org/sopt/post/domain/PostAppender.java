package org.sopt.post.domain;

import java.time.LocalDateTime;
import org.sopt.Member.domain.Member;
import org.sopt.post.enums.PostCategory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PostAppender {
    private final PostRepository postRepository;

    public PostAppender(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Post append(
            String title,
            String content,
            PostCategory postCategory,
            Member author
    ) {
        Post post = Post.create(
                title,
                content,
                postCategory,
                author.getId()
        );

        return postRepository.save(post);
    }
}

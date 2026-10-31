package org.sopt.post.domain;

import org.sopt.post.enums.PostCategory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PostUpdater {
    private final PostRepository postRepository;
    private final PostReader postReader;

    public PostUpdater(PostRepository postRepository, PostReader postReader) {
        this.postRepository = postRepository;
        this.postReader = postReader;
    }

    @Transactional
    public void updatePost(Long postId, String newTitle, String newContent, PostCategory postCategory) {
        Post post = postReader.read(postId);
        post.update(newTitle, newContent, postCategory);
        postRepository.save(post);
    }
}

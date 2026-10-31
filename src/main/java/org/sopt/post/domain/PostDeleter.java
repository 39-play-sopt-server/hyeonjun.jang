package org.sopt.post.domain;

import org.springframework.stereotype.Component;

@Component
public class PostDeleter {
    private final PostRepository postRepository;
    private final PostReader postReader;

    public PostDeleter(PostRepository postRepository, PostReader postReader) {
        this.postRepository = postRepository;
        this.postReader = postReader;
    }

    public void deletePost(Long id) {
        Post post = postReader.read(id);
        postRepository.delete(post);
    }
}

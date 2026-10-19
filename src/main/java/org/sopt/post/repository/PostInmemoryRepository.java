package org.sopt.post.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.sopt.post.domain.Post;

@Repository
public class PostInmemoryRepository {
    private static final HashMap<Long, Post> posts = new LinkedHashMap<>();

    public Post save(Post post) {
        posts.put(post.getId(), post);
        return post;
    }

    public List<Post> findAllPost() {
        return new ArrayList<>(posts.values());
    }

    public Post findById(Long id) {
        return posts.get(id);
    }

    public void deletebyId(Long id) {
        posts.remove(id);
    }
}

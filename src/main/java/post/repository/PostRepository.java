package post.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import post.domain.Post;

public class PostRepository {
    private static final HashMap<Long, Post> posts = new HashMap<>();

    public Post save(Post post) {
        posts.put(post.getId(), post);
        return post;
    }

    // ArrayList로 하려다가 조회만 하는데 가변 리스트는 필요없다는 생각에 List로
    public List<Post> findAllPost() {
        return new ArrayList<>(posts.values());
    }

    // HashMap 아니였다면 stream 으로 펼쳐서 filter 를 쓰고 했겠지..
    public Post findById(Long id) {
        return posts.get(id);
    }

    public void deletebyId(Long id) {
        posts.remove(id);
    }
}

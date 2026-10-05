
package post.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import post.domain.Post;
import post.domain.PostCategory;
import post.dto.request.CreatePostRequest;
import post.dto.request.UpdatePostRequest;
import post.execption.PostNotFoundException;
import post.repository.PostRepository;
import post.validator.PostValidator;

public class PostService {
    private Long nextId = 1L;
    private final PostRepository postRepository;
    private final PostValidator validator = new PostValidator();

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void createPost(CreatePostRequest postRequest) {

        validator.validateTitle(postRequest.title);
        validator.validateContent(postRequest.content);

        String createdAt = LocalDateTime.now().toString();
        PostCategory category = null;

        // 클라이언트가 소문자로 보냈을 시 방어적으로 설계하기 위함
        try {
            category = PostCategory.valueOf(
                    postRequest.category.toUpperCase(Locale.ROOT)
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("올바르지 않은 카테고리입니다.");
        }

        Post post = new Post(nextId++, postRequest.title, postRequest.content, category, createdAt, postRequest.author);

        postRepository.save(post);
    }

    public List<Post> getPosts() {
        List<Post> posts = postRepository.findAllPost();

        if (posts.isEmpty()) {
            throw new PostNotFoundException("게시글이 없습니다.");
        }

        return posts;
    }

    //조회할 때 코드가 3회 이상 반복돼서 빼버림
    // validator로 보냈었는데 Repository를 보내야해서 다시 여기로 옮김
    public Post validateAndGetPost(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("게시글 ID는 1 이상이어야 합니다.");
        }
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new PostNotFoundException("존재하지 않는 게시글입니다.");
        }
        return post;
    }

    public Post getPost(Long id) {
        return validateAndGetPost(id);
    }

    public Post updatePost(Long id, UpdatePostRequest postRequest) {
        // 수정 내용 검증
        validator.validateContent(postRequest.content);
        validator.validateTitle(postRequest.title);

        Post post = validateAndGetPost(id);

        PostCategory category = PostCategory.valueOf(
                postRequest.category.toUpperCase(Locale.ROOT)
        );

        post.update(
                postRequest.title,
                postRequest.content,
                category
        );

        return post;
    }


    public void deletePost(Long id) {

        Post post = validateAndGetPost(id);
        postRepository.deletebyId(id);
    }

}
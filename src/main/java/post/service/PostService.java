
package post.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import post.domain.Post;
import post.domain.PostCategory;
import post.dto.request.CreatePostRequest;
import post.dto.request.UpdatePostRequest;
import post.dto.response.PostResponse;
import post.execption.InvalidPostCategoryException;
import post.execption.PostNotFoundException;
import post.repository.PostRepository;
import post.validator.PostValidator;


@Service
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
        PostCategory category = parseCategory(postRequest.category);

        Post post = new Post(nextId++, postRequest.title, postRequest.content, category, createdAt, postRequest.author);

        postRepository.save(post);
    }

    public List<PostResponse> getPosts() {
        List<PostResponse> posts = postRepository.findAllPost().stream()
                .map(PostResponse::from)
                .toList();

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
            throw new PostNotFoundException();
        }
        return post;
    }

    public PostResponse getPost(Long id) {
        return PostResponse.from(validateAndGetPost(id));
    }

    public PostResponse updatePost(Long id, UpdatePostRequest postRequest) {
        // 수정 내용 검증
        validator.validateContent(postRequest.content);
        validator.validateTitle(postRequest.title);

        Post post = validateAndGetPost(id);

        PostCategory category = parseCategory(postRequest.category);

        post.update(
                postRequest.title,
                postRequest.content,
                category
        );

        return PostResponse.from(post);
    }


    public void deletePost(Long id) {

        Post post = validateAndGetPost(id);
        postRepository.deletebyId(id);
    }

    private PostCategory parseCategory(String category) {
        if (category == null) {
            throw new InvalidPostCategoryException();
        }
        try {
            return PostCategory.valueOf(category.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new InvalidPostCategoryException();
        }
    }

}

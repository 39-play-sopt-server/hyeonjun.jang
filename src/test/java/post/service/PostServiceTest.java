package post.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import post.dto.request.CreatePostRequest;
import post.execption.InvalidPostCategoryException;
import post.repository.PostRepository;

class PostServiceTest {

    @Test
    void createPostRejectsInvalidCategory() {
        PostService postService = new PostService(new PostRepository());
        CreatePostRequest request = new CreatePostRequest(
                "제목", "내용", "INVALID", "작성자"
        );

        assertThrows(
                InvalidPostCategoryException.class,
                () -> postService.createPost(request)
        );
    }
}

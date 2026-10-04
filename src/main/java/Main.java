import Global.ApiResponse;
import java.util.List;
import post.PostView;
import post.controller.PostController;
import post.domain.Post;
import post.dto.request.CreatePostRequest;
import post.dto.request.UpdatePostRequest;
import post.repository.PostRepository;
import post.service.PostService;

public class Main {
    public static void main(String[] args) {

        PostRepository postRepository = new PostRepository();
        PostView postView = new PostView();
        PostService postService = new PostService(postRepository);
        PostController postController = new PostController(postService);

        while (true) {
            int command = postView.inputCommand();
            switch (command) {
                case 1 -> {
                    String title = postView.inputTitle();
                    String content = postView.inputContent();
                    String category = postView.inputCategory();
                    String author = postView.inputAuthor();

                    CreatePostRequest createPostRequest = CreatePostRequest.of(title, content, category, author);

                    postView.printMessage(postController.createPost(createPostRequest));
                }
                case 2 -> {
                    ApiResponse<List<Post>> response = postController.getAllPost();
                    postView.printResponse(response);
                }
                case 3 -> {
                    Long id = postView.inputPostNumber("조회할 게시글 번호: ");

                    ApiResponse<Post> response = postController.getPost(id);

                    postView.printMessage(response);

                    if (response.getData() != null) {
                        postView.printPost(response.getData());
                    }
                }
                case 4 -> {
                    Long id = postView.inputPostNumber("수정할 게시글 번호: ");

                    String newTitle = postView.inputTitle();
                    String newContent = postView.inputContent();
                    String newCategory = postView.inputCategory();

                    UpdatePostRequest requset = new UpdatePostRequest(
                            newTitle, newContent, newCategory
                    );

                    postView.printMessage(
                            postController.updatePost(id, requset)
                    );
                }
                case 5 -> {
                    Long id = postView.inputPostNumber("삭제할 게시글 번호: ");
                    postView.printMessage(postController.deletePost(id));
                }
                case 6 -> {
                    postView.printMessage("프로그램을 종료합니다.");
                    return;
                }
                default -> postView.printMessage("잘못된 입력입니다.");
            }
        }
    }
}
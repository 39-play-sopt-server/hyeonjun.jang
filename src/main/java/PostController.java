import java.util.ArrayList;
import java.util.List;

public class PostController {

    private final PostView postView;
    private final List<Post> posts = new ArrayList<>();

    public PostController(PostView postView) {
        this.postView = postView;
    }

    public void run() {

        while (true) {
            int command = postView.inputCommand();

            switch (command) {
                case 1:
                    createPost();
                    break;

                case 2:
                    getPosts();
                    break;

                case 3:
                    getPost();
                    break;

                case 4:
                    updatePost();
                    break;

                case 5:
                    deletePost();
                    break;

                case 6:
                    postView.printMessage("프로그램을 종료합니다.");
                    return;

                default:
                    postView.printMessage("잘못된 입력입니다.");
            }
        }
    }

    private void createPost() {
        String title = postView.inputTitle();
        String content = postView.inputContent();

        Post post = new Post(title, content);
        posts.add(post);

        postView.printMessage("게시글이 작성되었습니다.");
    }

    private void getPosts() {
        postView.printPosts(posts);
    }

    private void getPost() {

        if (posts.isEmpty()) {
            postView.printMessage("게시글이 없습니다.");
            return;
        }

        int index = postView.inputPostNumber("조회할 게시글 번호: ");

        if (!isValidIndex(index)) {
            postView.printMessage("존재하지 않는 게시글입니다.");
            return;
        }

        Post post = posts.get(index);
        postView.printPost(post);
    }

    private void updatePost() {

        if (posts.isEmpty()) {
            postView.printMessage("게시글이 없습니다.");
            return;
        }

        int index = postView.inputPostNumber("수정할 게시글 번호: ");

        if (!isValidIndex(index)) {
            postView.printMessage("존재하지 않는 게시글입니다.");
            return;
        }

        Post post = posts.get(index);

        String newTitle = postView.inputTitle();
        String newContent = postView.inputContent();

        post.title = newTitle;
        post.content = newContent;

        postView.printMessage("게시글이 수정되었습니다.");
    }

    private void deletePost() {

        if (posts.isEmpty()) {
            postView.printMessage("게시글이 없습니다.");
            return;
        }

        int index = postView.inputPostNumber("삭제할 게시글 번호: ");

        if (!isValidIndex(index)) {
            postView.printMessage("존재하지 않는 게시글입니다.");
            return;
        }

        posts.remove(index);

        postView.printMessage("게시글이 삭제되었습니다.");
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < posts.size();
    }
}
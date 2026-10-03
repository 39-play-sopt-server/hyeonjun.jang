import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class PostView {

    private final Scanner scanner =
            new Scanner(System.in, StandardCharsets.UTF_8);

    public int inputCommand() {
        System.out.println("\n=== 게시판 ===");
        System.out.println("1. 게시글 작성");
        System.out.println("2. 게시글 목록 조회");
        System.out.println("3. 게시글 단건 조회");
        System.out.println("4. 게시글 수정");
        System.out.println("5. 게시글 삭제");
        System.out.println("6. 종료");
        System.out.print("선택: ");

        return Integer.parseInt(scanner.nextLine());
    }

    public String inputTitle() {
        System.out.print("제목: ");
        return scanner.nextLine();
    }

    public String inputContent() {
        System.out.print("내용: ");
        return scanner.nextLine();
    }

    public int inputPostNumber(String message) {
        System.out.print(message);
        return Integer.parseInt(scanner.nextLine()) - 1;
    }

    public void printPosts(List<Post> posts) {
        System.out.println("\n=== 게시글 목록 ===");

        if (posts.isEmpty()) {
            System.out.println("게시글이 없습니다.");
            return;
        }

        for (int i = 0; i < posts.size(); i++) {
            System.out.println((i + 1) + ". " + posts.get(i).title);
        }
    }

    public void printPost(Post post) {
        System.out.println("\n=== 게시글 ===");
        System.out.println("제목: " + post.title);
        System.out.println("내용: " + post.content);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
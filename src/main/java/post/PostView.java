package post;

import Global.ApiResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import post.domain.Post;

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

    public String inputAuthor() {
        System.out.print("저자: ");
        return scanner.nextLine();
    }

    public String inputCategory() {
        System.out.print("카테고리(NOTICE/FREE/QUESTION): ");
        return scanner.nextLine();
    }

    public Long inputPostNumber(String message) {
        System.out.print(message);
        return Long.parseLong(scanner.nextLine());
    }

    public void printPosts(List<Post> posts) {
        System.out.println("\n=== 게시글 목록 ===");

        if (posts.isEmpty()) {
            System.out.println("게시글이 없습니다.");
            return;
        }

        int index = 1;

        for (Post post : posts) {
            System.out.println(index + ". " + post.getTitle());
            index++;
        }
    }

    public void printPost(Post post) {
        System.out.println("\n=== 게시글 ===");
        System.out.println("제목: " + post.getTitle());
        System.out.println("내용: " + post.getContent());
        System.out.println("저자: " + post.getAuthor());
        System.out.println("작성시각: " + post.getCreatedAt());
        System.out.println("카테고리: " + post.getCategory());
    }

    public void printMessage(ApiResponse<?> response) {
        System.out.println(response.getMessage());
    }
    public void printMessage(String response) {
        System.out.println(response);
    }

    public void printResponse(ApiResponse<List<Post>> response) {
        System.out.println(response.getMessage());

        if (response.getData() != null) {
            printPosts(response.getData());
        }
    }



}
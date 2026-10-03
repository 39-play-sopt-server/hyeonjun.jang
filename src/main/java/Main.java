import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        PostView postView = new PostView();
        PostController postController = new PostController(postView);

        postController.run();
    }
}
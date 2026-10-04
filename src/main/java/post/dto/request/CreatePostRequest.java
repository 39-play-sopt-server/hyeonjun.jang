package post.dto.request;

public class CreatePostRequest {
    public String title;
    public String content;
    public String category;
    public String author;


    public CreatePostRequest(String title, String content, String category, String author) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
    }

    public static CreatePostRequest of(String title, String content, String category, String author) {
        return new CreatePostRequest(title, content,category, author);
    }

}
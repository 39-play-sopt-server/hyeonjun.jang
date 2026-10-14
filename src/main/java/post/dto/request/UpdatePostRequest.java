package post.dto.request;

public class UpdatePostRequest {
    public String title;
    public String content;
    public String category;

    public UpdatePostRequest(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}

package post.dto.response;

import post.domain.Post;
import post.domain.PostCategory;

public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private PostCategory category;
    private String createdAt;
    private String author;

    public PostResponse(Long id, String title, String content, PostCategory category, String createdAt,
                        String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdAt = createdAt;
        this.author = author;
    }

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCategory(),
                post.getCreatedAt(),
                post.getAuthor()
        );
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public PostCategory getCategory() {
        return category;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getAuthor() {
        return author;
    }
}

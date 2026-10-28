package org.sopt.post.domain;


import java.time.LocalDateTime;
import org.sopt.post.enums.PostCategory;


public class Post {
    private final Long id;
    private String title;
    private String content;
    private PostCategory category;
    private Long authorId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Post(Long id, String title, String content, PostCategory category, Long authorId,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Post create(String title, String content, PostCategory category, Long authorId) {
        return new Post(null, title, content, category, authorId, null, null);
    }

    public static Post createWithId(Long id, String title, String content, PostCategory category, Long authorId,
                                    LocalDateTime createdAt,
                                    LocalDateTime updatedAt) {
        return new Post(id, title, content, category, authorId, createdAt, updatedAt);
    }


    public void update(String title, String content, PostCategory category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    //Getter

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

    public Long getAuthorId() {
        return authorId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

package org.sopt.post.controller.dto.response;

import org.sopt.post.domain.Post;
import org.sopt.post.enums.PostCategory;

public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private PostCategory category;
    private int hits;
    private String createdAt;
    private String updatedAt;

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

    public int getHits() {
        return hits;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getAuthor() {
        return author;
    }

    private String author;

    public PostResponse(Long id, String title, String content, int hits, PostCategory category, String createdAt,
                        String updatedAt, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.hits = hits;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.author = author;
    }

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getHits(),
                post.getCategory(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getAuthor()
        );
    }

}

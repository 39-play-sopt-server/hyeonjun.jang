package org.sopt.post.domain;


import org.sopt.post.enums.PostCategory;


public class Post {
    private final Long id;
    private String title;
    private String content;
    private PostCategory category;
    private int hits;
    private final String createdAt;
    private String updatedAt;
    private final String author;

    public Post(Long id, String title, String content, PostCategory category, int hits, String createdAt,
                String updatedAt, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.hits = hits;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.author = author;
    }


    public void update(String title, String content, PostCategory category, String updatedAt) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.updatedAt = updatedAt;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public String getAuthor() {
        return author;
    }

    public int getHits() {
        return hits;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    // 조회수 증가
    public void increaseHits() {
        hits++;
    }
}
package org.sopt.like.domain;

public class Like {
    private final Long id;
    private final Long memberId;
    private final Long postId;

    public Like(Long id, Long memberId, Long postId) {
        this.id = id;
        this.memberId = memberId;
        this.postId = postId;
    }

    public static Like createWithId(Long id, Long memberId, Long postId) {
        return new Like(id, memberId, postId);
    }

    public static Like create(Long memberId, Long postId) {
        return new Like(null, memberId, postId);
    }

    // Getter

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getPostId() {
        return postId;
    }
}

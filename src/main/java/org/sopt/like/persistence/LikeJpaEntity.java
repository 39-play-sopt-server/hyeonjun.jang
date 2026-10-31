package org.sopt.like.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.sopt.Member.persistence.MemberJpaEntity;
import org.sopt.like.domain.Like;
import org.sopt.post.persistence.PostJpaEntity;

@Entity(name = "Like")
@Table(
        name = "likes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_like_post_id_member_id", columnNames = {"post_id", "member_id"})
        },
        indexes = {
                @Index(name = "idx_like_post_id", columnList = "post_id"),
        }
)
public class LikeJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, insertable = false, updatable = false)
    private PostJpaEntity post;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, insertable = false, updatable = false)
    private MemberJpaEntity member;

    protected LikeJpaEntity() {
    }

    public LikeJpaEntity(Long postId, Long memberId) {
        this.postId = postId;
        this.memberId = memberId;
    }

    public LikeJpaEntity create(Long postId, Long memberId) {
        return new LikeJpaEntity(postId, memberId);
    }

    public static LikeJpaEntity from(Like like) {
        return new LikeJpaEntity(like.getPostId(), like.getMemberId());
    }

    public Like toDomain() {
        return Like.createWithId(id, postId, memberId);
    }

    public Long getId() {
        return id;
    }
}

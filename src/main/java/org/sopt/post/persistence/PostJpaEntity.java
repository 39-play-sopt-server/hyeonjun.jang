package org.sopt.post.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.sopt.Member.persistence.MemberJpaEntity;
import org.sopt.common.entity.BaseTimeEntity;
import org.sopt.post.domain.Post;
import org.sopt.post.enums.PostCategory;

@Entity(name = "Post")
@Table(name = "posts")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE posts SET deleted_at = NOW() WHERE id = ?")
public class PostJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_category", nullable = false)
    private PostCategory postCategory;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false, insertable = false, updatable = false)
    private MemberJpaEntity author;


    protected PostJpaEntity() {
    }

    private PostJpaEntity(
            Long id,
            String title,
            String content,
            PostCategory postCategory,
            Long authorId

    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postCategory = postCategory;
        this.authorId = authorId;
    }

    public Long getId() {
        return id;
    }

    public Post toDomain() {
        return Post.createWithId(
                id,
                title,
                content,
                postCategory,
                authorId,
                getCreatedAt(),
                getUpdatedAt()
        );
    }

    public static PostJpaEntity fromDomain(Post post) {
        return new PostJpaEntity(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCategory(),
                post.getAuthorId()
        );
    }
}

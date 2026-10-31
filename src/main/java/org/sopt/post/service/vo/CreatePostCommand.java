package org.sopt.post.service.vo;

import org.sopt.post.enums.PostCategory;

public record CreatePostCommand(
        String title,
        String content,
        PostCategory category,
        Long authorId
) {
    public static CreatePostCommand of(
            String title,
            String content,
            PostCategory category,
            Long authorId
    ) {
        return new CreatePostCommand(title, content, category, authorId);
    }
}

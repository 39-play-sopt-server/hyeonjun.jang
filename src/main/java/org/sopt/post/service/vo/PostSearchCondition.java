package org.sopt.post.service.vo;

import org.sopt.post.enums.PostCategory;

public record PostSearchCondition(
        String keyword,
        Long authorId,
        PostCategory category
) {
}

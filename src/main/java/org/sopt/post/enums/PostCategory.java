package org.sopt.post.enums;

import org.sopt.common.exception.BusinessException;
import org.sopt.post.code.PostErrorCode;

public enum PostCategory {
    FREE, //자유
    HOT, //핫게
    SECRET, //비밀
    ;

    public static PostCategory from(String boardType) {
        try {
            return PostCategory.valueOf(boardType);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(PostErrorCode.POST_INVALID_CATEGORY);
        }
    }
}
package org.sopt.post.execption;

import org.sopt.global.exception.BusinessException;
import org.sopt.post.code.PostErrorCode;

public class PostInvalidCategory extends BusinessException {
    public PostInvalidCategory() {
        super(PostErrorCode.POST_INVALID_CATEGORY);
    }
}

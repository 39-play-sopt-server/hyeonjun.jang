package org.sopt.post.exceptions;

import org.sopt.common.exception.BusinessException;
import org.sopt.post.code.PostErrorCode;

public class PostInvalidCategory extends BusinessException {
    public PostInvalidCategory() {
        super(PostErrorCode.POST_INVALID_CATEGORY);
    }
}

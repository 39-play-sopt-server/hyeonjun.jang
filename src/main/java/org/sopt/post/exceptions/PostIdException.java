package org.sopt.post.exceptions;

import org.sopt.common.exception.BusinessException;
import org.sopt.post.code.PostErrorCode;

public class PostIdException extends BusinessException {
    public PostIdException() {
        super(PostErrorCode.POST_INVALID_ID);
    }
}

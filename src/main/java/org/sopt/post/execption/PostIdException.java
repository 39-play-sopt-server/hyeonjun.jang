package org.sopt.post.execption;

import org.sopt.global.exception.BusinessException;
import org.sopt.post.code.PostErrorCode;

public class PostIdException extends BusinessException {
    public PostIdException() {
        super(PostErrorCode.POST_INVALID_ID);
    }
}

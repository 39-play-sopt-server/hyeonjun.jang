package org.sopt.post.execption;

import org.sopt.common.exception.BusinessException;
import org.sopt.post.code.PostErrorCode;

public class PostContentException extends BusinessException {
    public PostContentException() {
        super(PostErrorCode.POST_CONTENT_REQUIRED);
    }
}

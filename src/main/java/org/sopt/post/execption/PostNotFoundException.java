package org.sopt.post.execption;

import org.sopt.common.exception.BusinessException;
import org.sopt.post.code.PostErrorCode;

public class PostNotFoundException extends BusinessException {

    public PostNotFoundException() {
        super(PostErrorCode.POST_NOT_FOUND);
    }
}
package org.sopt.post.execption;

import org.sopt.global.exception.BusinessException;
import org.sopt.post.code.PostErrorCode;

public class PostTitleException extends BusinessException {
    public PostTitleException() {
        super(PostErrorCode.POST_TITLE_REQUIRED);
    }
}

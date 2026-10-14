package org.sopt.post.execption;

import org.sopt.global.exception.BusinessException;
import org.sopt.post.code.PostErrorCode;

public class PostEmptyUpdateValue extends BusinessException {
    public PostEmptyUpdateValue() {
        super(
                PostErrorCode.POST_EMPTY_UPDATE_VALUE
        );

    }

}

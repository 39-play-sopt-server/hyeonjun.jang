package org.sopt.common.exception;

import org.sopt.common.code.GlobalErrorCode;

public class PageException extends BusinessException{
    public PageException() {
        super(GlobalErrorCode.INVALID_PAGE);
    }
}

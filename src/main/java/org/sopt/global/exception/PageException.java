package org.sopt.global.exception;

import org.sopt.global.code.GlobalErrorCode;

public class PageException extends BusinessException{
    public PageException() {
        super(GlobalErrorCode.INVALID_PAGE);
    }
}

package org.sopt.global.exception;

import org.sopt.global.code.GlobalErrorCode;

public class SizeException extends BusinessException {

    public SizeException() {
        super(GlobalErrorCode.INVALID_PAGE_SIZE);
    }
}

package org.sopt.common.exception;

import org.sopt.common.code.GlobalErrorCode;

public class SizeException extends BusinessException {

    public SizeException() {
        super(GlobalErrorCode.INVALID_PAGE_SIZE);
    }
}

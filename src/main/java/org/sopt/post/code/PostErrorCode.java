package org.sopt.post.code;

import org.sopt.global.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum PostErrorCode implements ErrorCode {
    POST_INVALID_ID(HttpStatus.BAD_REQUEST, "게시글 ID는 1 이상이어야 합니다."),
    POST_TITLE_REQUIRED(HttpStatus.BAD_REQUEST, "제목은 필수입니다."),
    POST_CONTENT_REQUIRED(HttpStatus.BAD_REQUEST, "내용은 필수입니다."),
    POST_INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "올바르지 않은 카테고리입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글이 없습니다."),
    POST_EMPTY_UPDATE_VALUE(HttpStatus.BAD_REQUEST, "수정할 값을 하나 이상 입력해야 합니다.");

    private final HttpStatus httpStatus;
    private final String message;

    PostErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

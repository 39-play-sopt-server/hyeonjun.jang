package org.sopt.common.dto;

import org.sopt.common.code.GlobalErrorCode;
import org.sopt.common.exception.BusinessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public record PageOffset(
        int page,
        int size
) {

    public static PageOffset of(int page, int size) {
        validate(page, size);

        return new PageOffset(page, size);
    }

    public PageRequest toPageRequest() {
        return PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    //밖에서는 사용하지 않기에 vaild는 여기에 선언
    private static void validate(int page, int size) {
        if (page <= 0) {
            throw new BusinessException(GlobalErrorCode.INVALID_PAGE);
        }

        if (size <= 0) {
            throw new BusinessException(GlobalErrorCode.INVALID_PAGE_SIZE);
        }
    }

}

package org.sopt.Member.code;

import org.sopt.common.code.SuccessCode;

public enum MemberSuccessCode implements SuccessCode {
    MEMBER_CREATED("멤버 추가 완료!"),
    MEMBER_UPDATED("멤버 수정 완료!"),
    MEMBER_DELETED("멤버 삭제 완료!"),
    MEMBERS_FOUND("멤버 목록 조회에 성공했습니다!"),
    MEMBERSMAP_FOUND("멤버 맵 조회에 성공했습니다!"),
    MEMBER_FOUND("멤버 조회에 성공했습니다!");

    private final String message;

    MemberSuccessCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

package org.sopt.Member.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.sopt.Member.controller.dto.CreateMemberRequest;
import org.sopt.Member.domain.Member;
import org.sopt.common.dto.CommonResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Member", description = "멤버 API")
public interface MemberApi {
    @Operation(summary = "회원 단건 조회", description = "회원 ID로 한 명의 회원 정보를 조회합니다.")
    @GetMapping("/{id}")
    public CommonResponse<Member> getMember(
            @PathVariable(required = true) Long id
    );

    @Operation(summary = "회원 생성", description = "새로운 회원을 생성합니다.")
    @PostMapping
    public CommonResponse<Void> createMember(
            @RequestBody(required = true) CreateMemberRequest createMemberRequest
    );

    @Operation(summary = "회원 목록 조회", description = "삭제되지 않은 전체 회원 목록을 조회합니다.")
    @GetMapping
    public CommonResponse<List<Member>> getAllMember();

    @Operation(summary = "회원 삭제", description = "회원 ID로 회원을 삭제합니다.")
    @DeleteMapping("/{id}")
    public CommonResponse<Void> deleteMember(
            @PathVariable(required = true) Long id
    );
}

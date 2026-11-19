package org.sopt.Member.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.sopt.Member.controller.dto.MemberResponse;
import org.sopt.common.dto.CommonResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Member", description = "멤버 API")
@SecurityRequirement(name = "bearerAuth")
public interface MemberApi {
    @Operation(summary = "회원 단건 조회", description = "회원 ID로 한 명의 회원 정보를 조회합니다.")
    @GetMapping("/{id}")
    public CommonResponse<MemberResponse> getMember(
            @PathVariable(required = true) Long id
    );

    @Operation(summary = "회원 목록 조회", description = "삭제되지 않은 전체 회원 목록을 조회합니다.")
    @GetMapping
    public CommonResponse<List<MemberResponse>> getAllMember();

    @Operation(summary = "회원 삭제", description = "회원 ID로 회원을 삭제합니다.")
    @DeleteMapping("/{id}")
    public CommonResponse<Void> deleteMember(
            @PathVariable(required = true) Long id
    );
}

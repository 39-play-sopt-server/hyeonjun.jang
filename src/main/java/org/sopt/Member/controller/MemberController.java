package org.sopt.Member.controller;

import java.util.List;
import org.sopt.Member.code.MemberSuccessCode;
import org.sopt.Member.controller.dto.MemberResponse;
import org.sopt.Member.domain.Member;
import org.sopt.Member.service.MemberService;
import org.sopt.common.dto.CommonResponse;
import org.sopt.security.oauth2.provider.PrincipalProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController implements MemberApi {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/me")
    public ResponseEntity<Long> me(
            @AuthenticationPrincipal PrincipalProvider principal
            ) {
        return ResponseEntity.ok(principal.memberId());
    }

    @GetMapping("/{id}")
    public CommonResponse<MemberResponse> getMember(
            @PathVariable(required = true) Long id
    ) {
        Member member = memberService.getMemberById(id);
        return CommonResponse.success(MemberSuccessCode.MEMBER_FOUND, MemberResponse.from(member));
    }

    @GetMapping
    public CommonResponse<List<MemberResponse>> getAllMember() {
        List<MemberResponse> members = memberService.getMemberAll()
                .stream()
                .map(MemberResponse::from)
                .toList();
        return CommonResponse.success(MemberSuccessCode.MEMBERS_FOUND, members);
    }

    @DeleteMapping("/{id}")
    public CommonResponse<Void> deleteMember(
            @PathVariable(required = true) Long id
    ) {
        memberService.delete(id);

        return CommonResponse.success(MemberSuccessCode.MEMBER_DELETED);
    }
}

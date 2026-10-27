package org.sopt.Member.controller;

import java.util.List;
import org.sopt.Member.code.MemberSuccessCode;
import org.sopt.Member.controller.dto.CreateMemberRequest;
import org.sopt.Member.domain.Member;
import org.sopt.Member.service.MemberService;
import org.sopt.common.dto.CommonResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class MemberController implements MemberApi {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public CommonResponse<Void> createMember(
            @RequestBody(required = true) CreateMemberRequest createMemberRequest
    ) {
        memberService.createMember(CreateMemberRequest.toCommand(createMemberRequest));

        return CommonResponse.success(MemberSuccessCode.MEMBER_CREATED);
    }
    @GetMapping
    public CommonResponse<Member> getMember(
            @RequestBody(required = true) Long id
    ) {
        Member member = memberService.getMemberById(id);
        return CommonResponse.success(MemberSuccessCode.MEMBER_FOUND, member);
    }

    @GetMapping
    public CommonResponse<List<Member>> getAllMember() {
        List<Member> members=  memberService.getMemberAll();
        return CommonResponse.success(MemberSuccessCode.MEMBERS_FOUND, members);
    }

    @DeleteMapping
    public CommonResponse<Void> deleteMember(
            @RequestBody(required = true) Long id
    ) {
        memberService.delete(id);

        return CommonResponse.success(MemberSuccessCode.MEMBER_DELETED);
    }
}

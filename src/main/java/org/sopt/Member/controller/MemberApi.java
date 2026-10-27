package org.sopt.Member.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.sopt.Member.domain.Member;
import org.sopt.common.dto.CommonResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Post", description = "멤버 API")
public interface MemberApi {


    @GetMapping
    public CommonResponse<Member> getMember(
            @RequestBody(required = true) Long id
    );


    @GetMapping
    public CommonResponse<List<Member>> getAllMember();

    @DeleteMapping
    public CommonResponse<Void> deleteMember(
            @RequestBody(required = true) Long id
    );
}

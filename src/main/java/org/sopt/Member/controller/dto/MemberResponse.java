package org.sopt.Member.controller.dto;

import org.sopt.Member.domain.Member;
import org.sopt.Member.enums.Gender;
import org.sopt.Member.enums.Role;

public record MemberResponse(
        Long id,
        String email,
        String name,
        String bio,
        Gender gender,
        Role role
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getBio(),
                member.getGender(),
                member.getRole()
        );
    }
}

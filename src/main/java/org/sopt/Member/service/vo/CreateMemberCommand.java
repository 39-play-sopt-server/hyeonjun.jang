package org.sopt.Member.service.vo;

import org.sopt.Member.enums.Gender;
import org.sopt.Member.enums.Role;

public record CreateMemberCommand(
        String email,
        String name,
        String bio,
        Gender gender,
        Role role
) {
    public static CreateMemberCommand of(
            String email,
            String name,
            String bio,
            Gender gender,
            Role role
    ){
        return new CreateMemberCommand(email, name, bio, gender, role);
    }
}

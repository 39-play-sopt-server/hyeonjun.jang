package org.sopt.Member.controller.dto;

import jakarta.validation.constraints.NotBlank;
import org.sopt.Member.enums.Gender;
import org.sopt.Member.enums.Role;
import org.sopt.Member.service.vo.CreateMemberCommand;

public record CreateMemberRequest(
        @NotBlank
        String email,
        @NotBlank
        String name,
        String bio,
        Gender gender,
        Role role

) {
    public static CreateMemberCommand toCommand(CreateMemberRequest request) {
        return new CreateMemberCommand(request.email(), request.name(), request.bio(), request.gender(), request.role());
    }
}

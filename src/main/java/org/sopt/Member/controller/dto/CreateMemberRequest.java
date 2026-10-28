package org.sopt.Member.controller.dto;

import jakarta.validation.constraints.NotBlank;
import org.sopt.Member.enums.Gender;
import org.sopt.Member.enums.Role;
import org.sopt.Member.service.vo.CreateMemberCommand;
import org.sopt.common.validation.ValidationMessage;

public record CreateMemberRequest(
        @NotBlank(message = ValidationMessage.EMAIL_REQUIRED)
        String email,
        @NotBlank(message = ValidationMessage.NAME_REQUIRED)
        String name,
        String bio,
        Gender gender,
        Role role

) {
    public static CreateMemberCommand toCommand(CreateMemberRequest request) {
        return new CreateMemberCommand(request.email(), request.name(), request.bio(), request.gender(), request.role());
    }
}

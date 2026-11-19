package org.sopt.Member.controller.dto;

import jakarta.validation.constraints.NotBlank;
import org.sopt.Member.enums.Gender;
import org.sopt.Member.enums.Role;
import org.sopt.common.validation.ValidationMessage;

public record CreateMemberRequest(
        @NotBlank(message = ValidationMessage.EMAIL_REQUIRED)
        String email,
        String password,
        @NotBlank(message = ValidationMessage.NAME_REQUIRED)
        String name,
        String bio,
        Gender gender,
        Role role

) {
}

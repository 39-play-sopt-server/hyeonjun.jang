package org.sopt.post.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.sopt.common.validation.ValidationMessage;
import org.sopt.post.enums.PostCategory;
import org.sopt.post.service.vo.CreatePostCommand;

public record CreatePostRequest(
        @NotBlank(message = ValidationMessage.TITLE_REQUIRED)
        String title,
        @NotBlank(message = ValidationMessage.CONTENT_REQUIRED)
        String content,
        PostCategory category,
        Long authorId
) {

    public CreatePostCommand toCommand() {
        return new CreatePostCommand(
                title,
                content,
                category,
                authorId
        );
    }
}
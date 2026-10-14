package org.sopt.post.controller.dto.request;

import org.sopt.post.service.vo.CreatePostCommand;

public record CreatePostRequest(
        String title,
        String content,
        String category,
        String author
) {

    public CreatePostCommand toCommand() {
        return new CreatePostCommand(
                title,
                content,
                category,
                author
        );
    }
}
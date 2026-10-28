package org.sopt.post.controller.dto.request;

import java.util.Locale;
import org.sopt.post.enums.PostCategory;
import org.sopt.post.service.vo.UpdatePostCommand;

public record UpdatePostRequest(
        String title,
        String content,
        String category
) {

    public UpdatePostCommand toCommand() {
        return new UpdatePostCommand(
                title,
                content,
                PostCategory.from(category.toUpperCase(Locale.ROOT))
        );
    }
}
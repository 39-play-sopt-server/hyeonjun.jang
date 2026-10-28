package org.sopt.post.service.vo;

import org.sopt.post.enums.PostCategory;

public record UpdatePostCommand(
        String title,
        String content,
        PostCategory category
) {
//    public static UpdatePostCommand of(String title, String content, String category) {
//        return new UpdatePostCommand(title, content, category);
//    }
}

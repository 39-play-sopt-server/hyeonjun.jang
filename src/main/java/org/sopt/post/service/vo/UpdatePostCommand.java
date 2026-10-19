package org.sopt.post.service.vo;

public record UpdatePostCommand(
        String title,
        String content,
        String category
) {
//    public static UpdatePostCommand of(String title, String content, String category) {
//        return new UpdatePostCommand(title, content, category);
//    }
}

package org.sopt.post.service.vo;

public record CreatePostCommand(
        String title,
        String content,
        String category,
        String author
) {
//    public static CreatePostCommand of(
//            String title,
//            String content,
//            String category,
//            String author
//    ) {
//        return new CreatePostCommand(title, content, category, author);
//    }

}

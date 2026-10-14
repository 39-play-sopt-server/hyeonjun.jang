package org.sopt.post.service.vo;

public record UpdatePostCommand(
        String title,
        String content,
        String category
) {
    @Override
    public String title() {
        return title;
    }

    @Override
    public String content() {
        return content;
    }

    @Override
    public String category() {
        return category;
    }

//    public static UpdatePostCommand of(String title, String content, String category) {
//        return new UpdatePostCommand(title, content, category);
//    }
}

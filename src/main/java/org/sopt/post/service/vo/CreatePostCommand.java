package org.sopt.post.service.vo;

public record CreatePostCommand(
        String title,
        String content,
        String category,
        String author
) {
    @Override
    public String title() {
        return title;
    }

    @Override
    public String content() {
        return content;
    }

    public String category() {
        return category;
    }

    @Override
    public String author() {
        return author;
    }

//    public static CreatePostCommand of(
//            String title,
//            String content,
//            String category,
//            String author
//    ) {
//        return new CreatePostCommand(title, content, category, author);
//    }

}

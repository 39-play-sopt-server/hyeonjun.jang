package org.sopt.post.validator;

import org.sopt.post.execption.PostContentException;
import org.sopt.post.execption.PostTitleException;
import org.springframework.stereotype.Component;

@Component
public class PostValidator {
    public static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new PostTitleException();
        }
    }

    public static void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new PostContentException();
        }
    }
}

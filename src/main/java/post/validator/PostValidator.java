package post.validator;

// Exception 파일을 따로 만드는 방법도 있었겠지만 나중에 Spring 도입하면 더 쉽게 할 수 있지 않나.. 라는 생각으로 xx

public class PostValidator {
    // 한 번에 할 수 도 있겠지만. 나는 업데이트에도 쓸 것이다.
    public void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수야.");
        }
    }

    public void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("내용 작성하자.");
        }
    }
}

package post.dto.response;

import java.time.LocalDateTime;
import post.domain.PostCategory;

public class PostResponse {
    Long id;
    String title;
    String content;
    PostCategory category;
    LocalDateTime createdAt;
    String author;
}
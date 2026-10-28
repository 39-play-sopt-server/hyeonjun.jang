package org.sopt.post.controller.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.sopt.Member.domain.Member;
import org.sopt.common.dto.PageResult;
import org.sopt.post.domain.Post;
import org.sopt.post.enums.PostCategory;

public record PostListResponse(
        List<PostItem> postList,
        Long totalPages
) {


    public static PostListResponse of(PageResult<Post> posts, Map<Long, Member> authorMap, Map<Long, Long> likeMap) {
        return new PostListResponse(
                posts.contents()
                        .stream()
                        .map(post ->
                                PostItem.from(
                                        post,
                                        authorMap.get(post.getAuthorId()),
                                        likeMap.getOrDefault(post.getId(), 0L)
                                )
                        ).toList(), posts.totalPages()
        );
    }

    record PostItem(
            Long id,
            String title,
            String content,
            String authorName,
            PostCategory postCategory,
            long likeCount,
            LocalDateTime createdAt
    ) {
        public static PostItem from(Post post, Member author, long likeCount) {
            return new PostItem(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    author.getName(),
                    post.getCategory(),
                    likeCount,
                    post.getCreatedAt()
            );
        }
    }

}

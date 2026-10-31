package org.sopt.post.controller.dto.response;

import org.sopt.Member.domain.Member;
import org.sopt.post.domain.Post;
import org.sopt.post.enums.PostCategory;

public record PostResponse(
        Long id,
        String title,
        String content,
        PostCategory category,
        long likeCount,
        AuthorInfo author
) {

    public static PostResponse from(Post post, Member member, long likeCount) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCategory(),
                likeCount,
                AuthorInfo.from(member)
        );
    }

    public record AuthorInfo(
            Long authorId,
            String name,
            String bio
    ) {
        public static AuthorInfo from(Member member) {
            return new AuthorInfo(member.getId(), member.getName(), member.getBio());
        }
    }
}

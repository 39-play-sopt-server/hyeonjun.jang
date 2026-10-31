package org.sopt.like.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.sopt.Member.domain.Member;
import org.sopt.post.domain.Post;
import org.springframework.stereotype.Component;

@Component
public class LikeReader {
    private final LikeRepository likeRepository;

    public LikeReader(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public boolean isLiked(Post post, Member member) {
        return likeRepository.existsByPostIdAndMemberId(post.getId(), member.getId());
    }

    public Map<Long, Long> countLikes(List<Post> posts) {
        Set<Long> postIds = posts.stream()
                .map(Post::getId)
                .collect(Collectors.toUnmodifiableSet());
        return likeRepository.countByPostIds(postIds);
    }

    public Long countLike(Long postId) {
        return likeRepository.countByPostId(postId);
    }
}

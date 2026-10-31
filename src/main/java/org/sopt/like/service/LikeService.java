package org.sopt.like.service;

import java.util.List;
import java.util.Map;
import org.sopt.Member.domain.Member;
import org.sopt.Member.domain.MemberReader;
import org.sopt.like.domain.Like;
import org.sopt.like.domain.LikeProcessor;
import org.sopt.like.domain.LikeReader;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostReader;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    private final LikeReader likeReader;
    private final LikeProcessor likeProcessor;
    private final PostReader postReader;
    private final MemberReader memberReader;

    public LikeService(LikeReader likeReader, LikeProcessor likeProcessor, PostReader postReader,
                       MemberReader memberReader) {
        this.likeReader = likeReader;
        this.likeProcessor = likeProcessor;
        this.postReader = postReader;
        this.memberReader = memberReader;
    }

    public boolean toggleLike(Long postId, Long memberId) {
        Post post = postReader.read(postId);
        Member member = memberReader.read(memberId);

        return likeProcessor.toggleLike(post, member);
    }

    public Map<Long, Long> countLikes(List<Post> posts) {
        return likeReader.countLikes(posts);
    }

    public Long countLike(Long postId) {
        return likeReader.countLike(postId);
    }
}

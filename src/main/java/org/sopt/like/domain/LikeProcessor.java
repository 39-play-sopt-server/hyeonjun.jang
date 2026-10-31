package org.sopt.like.domain;


import org.sopt.Member.domain.Member;
import org.sopt.post.domain.Post;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//  좋아요를 누른 상태인지 확인한 뒤 생성/삭제를만 담당
@Component
public class LikeProcessor {
    private final LikeRepository likeRepository;
    private final LikeReader likeReader;

    public LikeProcessor(LikeRepository likeRepository, LikeReader likeReader) {
        this.likeRepository = likeRepository;
        this.likeReader = likeReader;
    }

    @Transactional
    public boolean toggleLike(Post post, Member member) {
        if (likeReader.isLiked(post, member)) {
            likeRepository.deleteByPostIdAndMemberId(post.getId(), member.getId());
            return false;
        }
        likeRepository.save(Like.create(member.getId(), post.getId()));
        return true;
    }


}

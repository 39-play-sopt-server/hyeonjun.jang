package org.sopt.post.facade;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import java.util.stream.Collectors;
import org.sopt.Member.domain.Member;
import org.sopt.Member.service.MemberService;
import org.sopt.common.dto.PageOffset;
import org.sopt.common.dto.PageResult;
import org.sopt.like.domain.Like;
import org.sopt.like.service.LikeService;
import org.sopt.post.controller.dto.response.PostListResponse;
import org.sopt.post.controller.dto.response.PostResponse;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostReader;
import org.sopt.post.enums.PostCategory;
import org.sopt.post.service.PostService;
import org.sopt.post.service.vo.CreatePostCommand;
import org.sopt.post.service.vo.PostSearchCondition;
import org.sopt.post.service.vo.UpdatePostCommand;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class PostFacade {
    private final PostService postService;
    private final LikeService likeService;
    private final MemberService memberService;

    public PostFacade(PostService postService, LikeService likeService, MemberService memberService) {
        this.postService = postService;
        this.likeService = likeService;
        this.memberService = memberService;
    }

    @Transactional
    public void createPost(CreatePostCommand command) {
        postService.createPost(command);
    }

    public PostListResponse getAllPosts(PageOffset pageOffset, PostCategory postCategory) {
        PageResult<Post> posts = postService.getAllPosts(pageOffset, postCategory);
        Map<Long, Long> likeMap = likeService.countLikes(posts.contents());
        Map<Long, Member> memberMap = fetchAuthorMap(posts.contents());

        return PostListResponse.of(posts, memberMap, likeMap);

    }

    public PostResponse getPost(Long postId) {
        Post post = postService.getPostById(postId);
        Member author = memberService.getMemberById(post.getAuthorId());
        Long like = likeService.countLike(postId);

        return PostResponse.from(post, author, like);
    }

    public PostListResponse searchPosts(PostSearchCondition condition, PageOffset pageOffset) {
        PageResult<Post> posts = postService.searchPosts(condition, pageOffset);
        Map<Long, Member> authorMap = fetchAuthorMap(posts.contents());
        Map<Long, Long> likeMap = likeService.countLikes(posts.contents());

        return PostListResponse.of(posts, authorMap, likeMap);
    }

    @Transactional
    public void updatePost(Long postId, UpdatePostCommand command) {
        postService.updatePost(postId, command);
    }

    @Transactional
    public void deletePost(Long postId) {
        postService.deletePost(postId);
    }

    @Transactional
    public boolean toggleLike(Long postId, Long memberId) {
        return likeService.toggleLike(postId, memberId);
    }


    private Map<Long, Member> fetchAuthorMap(List<Post> posts) {
        Set<Long> authorIds = posts
                .stream()
                .map(Post::getAuthorId)
                .collect(Collectors.toUnmodifiableSet());

        return memberService.getMemberMapByIds(authorIds);
    }
}

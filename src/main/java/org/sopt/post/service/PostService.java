
package org.sopt.post.service;

import org.sopt.Member.domain.Member;
import org.sopt.Member.domain.MemberReader;
import org.sopt.common.dto.PageOffset;
import org.sopt.common.dto.PageResult;
import org.sopt.post.domain.PostAppender;
import org.sopt.post.domain.PostDeleter;
import org.sopt.post.domain.PostFinder;
import org.sopt.post.domain.PostReader;
import org.sopt.post.domain.PostUpdater;
import org.springframework.transaction.annotation.Transactional;
import org.sopt.post.service.vo.CreatePostCommand;
import org.sopt.post.service.vo.PostSearchCondition;
import org.sopt.post.service.vo.UpdatePostCommand;
import org.springframework.stereotype.Service;
import org.sopt.post.domain.Post;
import org.sopt.post.enums.PostCategory;

@Service
@Transactional(readOnly = true) // Dirty reading is 우웩
public class PostService {
    private final PostAppender postAppender;
    private final MemberReader memberReader;
    private final PostFinder postFinder;
    private final PostReader postReader;
    private final PostUpdater postUpdater;
    private final PostDeleter postDeleter;

    public PostService(PostAppender postAppender, MemberReader memberReader, PostFinder postFinder,
                       PostReader postReader, PostUpdater postUpdater, PostDeleter postDeleter) {
        this.postAppender = postAppender;
        this.memberReader = memberReader;
        this.postFinder = postFinder;
        this.postReader = postReader;
        this.postUpdater = postUpdater;
        this.postDeleter = postDeleter;
    }

    @Transactional
    public void createPost(CreatePostCommand command) {
        Member member = memberReader.read(command.authorId());

        postAppender.append(
                command.title(),
                command.content(),
                command.category(),
                member
        );
    }

    public PageResult<Post> getAllPosts(PageOffset pageOffset, PostCategory category) {
        return PageResult.from(postFinder.find(category, pageOffset));
    }

    public Post getPostById(Long id) {
        return postReader.read(id);
    }

    public PageResult<Post> searchPosts(PostSearchCondition condition, PageOffset pageOffset) {
        return PageResult.from(postFinder.search(condition, pageOffset));
    }


    @Transactional
    public void updatePost(Long id, UpdatePostCommand command) {
        postUpdater.updatePost(id, command.title(), command.content(), command.category());
    }


    @Transactional
    public void deletePost(Long id) {
        postDeleter.deletePost(id);
    }

}

package org.sopt.post.controller;

import jakarta.validation.Valid;
import java.util.Locale;
import org.sopt.common.dto.CommonResponse;
import org.sopt.common.dto.PageOffset;
import org.sopt.post.code.PostSuccessCode;
import org.sopt.post.controller.dto.request.CreatePostRequest;
import org.sopt.post.controller.dto.request.UpdatePostRequest;
import org.sopt.post.controller.dto.response.LikeToggleResponse;
import org.sopt.post.controller.dto.response.PostListResponse;
import org.sopt.post.enums.PostCategory;
import org.sopt.post.facade.PostFacade;
import org.sopt.post.service.vo.PostSearchCondition;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sopt.post.controller.dto.response.PostResponse;


@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController implements PostApi {
    private final PostFacade postFacade;

    public PostController(PostFacade postFacade) {
        this.postFacade = postFacade;
    }

    @PostMapping
    public CommonResponse<Void> createPost(
            @Valid @RequestBody(required = true) CreatePostRequest request
    ) {
        postFacade.createPost(request.toCommand());
        return CommonResponse.success(PostSuccessCode.POST_CREATED);

    }

    @GetMapping
    public CommonResponse<PostListResponse> readPosts(
            @RequestParam(required = false, defaultValue = "FREE") String category,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        PostListResponse result = postFacade.getAllPosts(
                PageOffset.of(page, size),
                PostCategory.from(category.toUpperCase(Locale.ROOT))
        );

        return CommonResponse.success(PostSuccessCode.POSTS_FOUND, result);
    }

    @GetMapping("/search")
    public CommonResponse<PostListResponse> searchPosts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        PostSearchCondition condition = new PostSearchCondition(
                keyword,
                authorId,
                category == null || category.isBlank()
                        ? null
                        : PostCategory.from(category.toUpperCase(Locale.ROOT))
        );
        PostListResponse response = postFacade.searchPosts(condition, PageOffset.of(page, size));
        return CommonResponse.success(PostSuccessCode.POST_FOUND, response);
    }

    @GetMapping(path = "/{postId}")
    public CommonResponse<PostResponse> readPost(
            @PathVariable(name = "postId") Long postId
    ) {
        PostResponse response = postFacade.getPost(postId);

        return CommonResponse.success(PostSuccessCode.POST_FOUND, response);

    }

    @PatchMapping(path = "/{postId}")
    public CommonResponse<Void> updatePost(
            @PathVariable(name = "postId") Long postId,
            @RequestBody(required = true) UpdatePostRequest request
    ) {
        postFacade.updatePost(postId, request.toCommand());

        return CommonResponse.success(PostSuccessCode.POST_UPDATED);
    }

    @DeleteMapping(path = "/{postId}")
    public CommonResponse<Void> deletePost(
            @PathVariable(name = "postId") Long postId
    ) {
        postFacade.deletePost(postId);
        return CommonResponse.success(PostSuccessCode.POST_DELETED);
    }

    @PostMapping("/{postId}/like/{memberId}")
    public CommonResponse<LikeToggleResponse> toggleLike(
            @PathVariable Long postId,
            @PathVariable Long memberId
    ) {
        boolean liked = postFacade.toggleLike(postId, memberId);
        PostSuccessCode code = liked
                ? PostSuccessCode.POST_LIKE
                : PostSuccessCode.POST_UNLIKE;

        return CommonResponse.success(code, new LikeToggleResponse(liked));
    }
}

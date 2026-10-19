package org.sopt.post.controller;

import java.util.List;
import org.sopt.global.dto.CommonResponse;
import org.sopt.post.code.PostSuccessCode;
import org.sopt.post.controller.dto.request.CreatePostRequest;
import org.sopt.post.controller.dto.request.UpdatePostRequest;
import org.sopt.post.facade.PostFacade;
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
            @RequestBody(required = true) CreatePostRequest request
    ) {
        postFacade.createPost(request.toCommand());

        return CommonResponse.success(PostSuccessCode.POST_CREATED);

    }

    @GetMapping
    public CommonResponse<List<PostResponse>> readPosts(
            @RequestParam(required = false, defaultValue = "FREE") String category,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        List<PostResponse> result = postFacade.getPosts(category, page, size);

        return CommonResponse.success(PostSuccessCode.POSTS_FOUND, result);
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
}

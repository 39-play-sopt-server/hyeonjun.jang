package post.controller;

import global.ApiResponse;
import global.ResponseCode;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import post.dto.request.CreatePostRequest;
import post.dto.request.UpdatePostRequest;
import post.dto.response.PostResponse;
import post.execption.InvalidPostCategoryException;
import post.execption.PostNotFoundException;
import post.service.PostService;


@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ApiResponse<?> createPost(
            @RequestBody(required = true) CreatePostRequest request
    ) {
        try {
            postService.createPost(request);
            return ApiResponse.success(ResponseCode.CREATED);
        } catch (InvalidPostCategoryException e) {
            return ApiResponse.fail(ResponseCode.POST_INVALID_CATEGORY);
        } catch (IllegalArgumentException e) {
            return ApiResponse.fail(ResponseCode.BAD_REQUEST);
        }
    }

    @GetMapping
    public ApiResponse<List<PostResponse>> readPosts(
            @RequestParam(name = "page", defaultValue = "1") int page) {
        return ApiResponse.success(ResponseCode.OK, postService.getPosts());
    }

    @GetMapping(path = "/{postId}")
    public ApiResponse<PostResponse> readPost(
            @PathVariable(name = "postId") Long postId
    ) {
        try {
            PostResponse result = postService.getPost(postId);
            return ApiResponse.success(ResponseCode.OK, result);
        } catch (PostNotFoundException e) {
            return ApiResponse.fail(ResponseCode.POST_NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ApiResponse.fail(ResponseCode.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{postId}")
    public ApiResponse<PostResponse> updatePost(
            @PathVariable(name = "postId") Long postId,
            @RequestBody(required = true) UpdatePostRequest request
    ) {
        try {
            PostResponse result = postService.updatePost(postId, request);
            return ApiResponse.success(ResponseCode.UPDATED, result);
        } catch (InvalidPostCategoryException e) {
            return ApiResponse.fail(ResponseCode.POST_INVALID_CATEGORY);
        } catch (PostNotFoundException e) {
            return ApiResponse.fail(ResponseCode.POST_NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ApiResponse.fail(ResponseCode.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{postId}")
    public ApiResponse<?> deletePost(
            @PathVariable(name = "postId") Long postId
    ) {
        try {
            postService.deletePost(postId);
            return ApiResponse.success(ResponseCode.DELETED);
        } catch (PostNotFoundException e) {
            return ApiResponse.fail(ResponseCode.POST_NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ApiResponse.fail(ResponseCode.BAD_REQUEST);
        }
    }

}

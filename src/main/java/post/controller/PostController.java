package post.controller;

import global.ApiResponse;
import global.ResponseCode;
import java.util.List;
import java.util.NoSuchElementException;
import post.domain.Post;
import post.dto.request.CreatePostRequest;
import post.dto.request.UpdatePostRequest;
import post.service.PostService;

public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    public ApiResponse createPost(CreatePostRequest request) {
        postService.createPost(request);
        return ApiResponse.success(ResponseCode.CREATED);
    }

    public ApiResponse<List<Post>> getAllPost() {
        try {
            return ApiResponse.success(ResponseCode.OK, postService.getPosts());
        } catch (NoSuchElementException e) {
            return ApiResponse.fail(ResponseCode.POST_NOT_FOUND);
        }
    }

    public ApiResponse<Post> getPost(Long id) {
        try {
            Post result = postService.getPost(id);
            return ApiResponse.success(ResponseCode.OK, result);
        } catch (NoSuchElementException e) {
            return ApiResponse.fail(ResponseCode.POST_NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ApiResponse.fail(ResponseCode.BAD_REQUEST);
        }
    }

    public ApiResponse<Post> updatePost(Long id, UpdatePostRequest request) {
        try {
            Post result = postService.updatePost(id, request);
            return ApiResponse.success(ResponseCode.UPDATED, result);
        } catch (NoSuchElementException e) {
            return ApiResponse.fail(ResponseCode.POST_NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ApiResponse.fail(ResponseCode.BAD_REQUEST);
        }
    }

    public ApiResponse deletePost(Long id) {
        try {
            postService.deletePost(id);
            return ApiResponse.success(ResponseCode.DELETED);
        } catch (NoSuchElementException e) {
            return ApiResponse.fail(ResponseCode.POST_NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ApiResponse.fail(ResponseCode.BAD_REQUEST);
        }
    }

}
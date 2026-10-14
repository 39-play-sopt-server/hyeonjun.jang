package org.sopt.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.sopt.global.dto.CommonResponse;
import org.sopt.global.exception.PageException;
import org.sopt.global.exception.SizeException;
import org.sopt.global.swagger.ApiExceptions;
import org.sopt.post.controller.dto.request.CreatePostRequest;
import org.sopt.post.controller.dto.request.UpdatePostRequest;
import org.sopt.post.controller.dto.response.PostResponse;
import org.sopt.post.execption.PostContentException;
import org.sopt.post.execption.PostEmptyUpdateValue;
import org.sopt.post.execption.PostIdException;
import org.sopt.post.execption.PostInvalidCategory;
import org.sopt.post.execption.PostNotFoundException;
import org.sopt.post.execption.PostTitleException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Post", description = "게시글 API")
public interface PostApi {
    @ApiExceptions({
            PostContentException.class, PostTitleException.class, PostInvalidCategory.class
    })
    @Operation(summary = "게시글 작성", description = "새로운 게시글을 생성합니다.")
    CommonResponse<Void> createPost(@RequestBody CreatePostRequest request);

    @ApiExceptions({PageException.class, SizeException.class, PostInvalidCategory.class})
    @Operation(summary = "게시글 목록 조회", description = "게시판 종류, 페이지 등에 따른 게시글 목록을 조회합니다.")
    CommonResponse<List<PostResponse>> readPosts(
            @RequestParam(required = false, defaultValue = "FREE") String category,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    );

    @ApiExceptions({PostNotFoundException.class, PostIdException.class})
    @Operation(summary = "게시글 상세 조회", description = "게시글 ID로 게시글 상세 정보를 조회합니다.")
    CommonResponse<PostResponse> readPost(@PathVariable Long postId);

    @ApiExceptions({PostNotFoundException.class, PostEmptyUpdateValue.class, PostIdException.class,
            PostContentException.class, PostTitleException.class, PostInvalidCategory.class})
    @Operation(summary = "게시글 수정", description = "게시글 ID로 게시글 제목, 내용, 카테고리를 수정합니다.")
    CommonResponse<Void> updatePost(@PathVariable Long postId, @RequestBody UpdatePostRequest request);

    @ApiExceptions({PostNotFoundException.class, PostIdException.class})
    @Operation(summary = "게시글 삭제", description = "게시글 ID로 게시글을 삭제합니다.")
    CommonResponse<Void> deletePost(@PathVariable Long postId);

}

package org.sopt.post.facade;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.sopt.post.controller.dto.response.PostResponse;
import org.sopt.post.service.PostService;
import org.sopt.post.service.vo.CreatePostCommand;
import org.sopt.post.service.vo.UpdatePostCommand;
import org.springframework.stereotype.Component;

// 현재 로직에서는 퍼사드 패턴은 사용하지 않아도 됩니다. 하지만 3,4주차 세미나 떄 JPA와 좋아요가 붙는다면에 가정을 두었습니다.
// 저처럼 굳이 사서 고생하겠다면 추천드립니다...
@Component
public class PostFacade {
    private final PostService postService;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    //ReentrantLock은 읽기 쓰기를 분리하지 않고 그냥 막아버린다고 해서...

    public PostFacade(PostService postService) {
        this.postService = postService;
    }

    public void CreatePost(CreatePostCommand command) {
        lock.writeLock().lock();
        try {
            postService.createPost(command);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<PostResponse> getPosts(String category, int page, int size) {
        lock.readLock().lock();
        try {
            return postService.getPosts(category, page, size).stream()
                    .map(PostResponse::from)
                    .toList();
        } finally {
            lock.readLock().unlock();
        }
    }

    public PostResponse getPost(Long postId) {
        lock.writeLock().lock();
        try {
            return PostResponse.from(postService.getPost(postId));
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void UpdatePost(Long postId, UpdatePostCommand command) {
        lock.writeLock().lock();
        try {
            postService.updatePost(postId, command);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void deletePost(Long postId) {
        lock.writeLock().lock();
        try {
            postService.deletePost(postId);
        } finally {
            lock.writeLock().unlock();
        }
    }
}

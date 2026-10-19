
package org.sopt.post.service;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.sopt.common.exception.PageException;
import org.sopt.common.exception.SizeException;
import org.sopt.post.execption.PostEmptyUpdateValue;
import org.sopt.post.execption.PostIdException;
import org.sopt.post.execption.PostInvalidCategory;
import org.sopt.post.repository.PostRepository;
import org.sopt.post.service.vo.CreatePostCommand;
import org.sopt.post.service.vo.UpdatePostCommand;
import org.springframework.stereotype.Service;
import org.sopt.post.domain.Post;
import org.sopt.post.enums.PostCategory;
import org.sopt.post.execption.PostNotFoundException;
import org.sopt.post.validator.PostValidator;

@Service
@Transactional(readOnly = true) // Dirty reading is 우웩
public class PostService {
    private final PostRepository postRepository;


    public PostService(PostRepository postRepository
    ) {
        this.postRepository = postRepository;
    }

    @Transactional
    public void createPost(CreatePostCommand command) {

        //검증 먼저
        PostValidator.validateTitle(command.title());
        PostValidator.validateContent(command.content());

        PostCategory category = parseCategory(command.category());

        Post post = new Post(command.title(), command.content(), category, 0,
                command.author());

        postRepository.save(post);
    }

    public List<Post> getPosts(String category, int page, int size) {
        if (page < 1) {
            throw new PageException();
        }
        if (size < 1) {
            throw new SizeException();
        }

        PostCategory parsedCategory = parseCategory(category);

        List<Post> posts = postRepository.findAll().stream()
                .filter(p -> p.getCategory() == parsedCategory)
                .toList();

        // 리스트 인덱스는 0부터 시작하니까 -1 을 해줘야함
        int fromIndex = (page - 1) * size;

        // 아예 범위를 벗어나면 빈 목록을 반환
        if (fromIndex >= posts.size()) {
            return List.of();
        }

        // 마지막 페이지는 10개가 아닐 수 있다.
        int toIndex = Math.min(fromIndex + size, posts.size());

        return posts.subList(fromIndex, toIndex);
    }

    public Post validateAndGetPost(Long id) {
        if (id <= 0) {
            throw new PostIdException();
        }
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new PostNotFoundException();
        }
        return post.orElse(null);
    }

    public Post getPost(Long id) {
        Post post = validateAndGetPost(id);

        post.increaseHits();

        return post;
    }


    @Transactional
    public void updatePost(Long id, UpdatePostCommand command) {
        if (command.title() == null && command.content() == null && command.category() == null) {
            throw new PostEmptyUpdateValue();
        }

        Post post = validateAndGetPost(id);

        String title = post.getTitle();
        String content = post.getContent();
        PostCategory category = post.getCategory();

        if (command.title() != null) {
            PostValidator.validateTitle(command.title());
            title = command.title();
        }

        if (command.content() != null) {
            PostValidator.validateContent(command.content());
            content = command.content();
        }

        if (command.category() != null) {
            category = parseCategory(command.category());
        }

        post.update(title, content, category);
    }


    @Transactional
    public void deletePost(Long id) {
        validateAndGetPost(id);
        postRepository.deleteById(id);
    }

    private PostCategory parseCategory(String category) {
        if (category == null) {
            throw new PostInvalidCategory();
        }
        try {
            return PostCategory.valueOf(category.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new PostInvalidCategory();
        }
    }

}

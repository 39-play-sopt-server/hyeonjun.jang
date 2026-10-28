package org.sopt.post.persistence;

import org.sopt.post.enums.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends
        JpaRepository<PostJpaEntity, Long>,
        PostJpaRepositoryCustom {

    Page<PostJpaEntity> findAll(Pageable pageable);

    Page<PostJpaEntity> findAllByPostCategory(
            PostCategory postCategory,
            Pageable pageable
    );
}
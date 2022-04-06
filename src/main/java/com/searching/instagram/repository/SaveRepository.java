package com.searching.instagram.repository;

import com.searching.instagram.entity.LikeEntity;
import com.searching.instagram.entity.SaveEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaveRepository extends JpaRepository<SaveEntity, Long>, JpaSpecificationExecutor<SaveEntity> {
    Optional<SaveEntity> findByProfileIdAndPostId(Long profileId, Long postId);

    List<SaveEntity> findByPostId(Long postId, Pageable pageable);
    List<SaveEntity> findByProfileId(Long profileId, Pageable pageable);
}

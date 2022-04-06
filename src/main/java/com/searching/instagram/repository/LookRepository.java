package com.searching.instagram.repository;

import com.searching.instagram.entity.LookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LookRepository extends JpaRepository<LookEntity, Long>, JpaSpecificationExecutor<LookEntity> {
    List<LookEntity> findByPostId(Long postId);

    List<LookEntity> findByProfileId(Long profileId);

    boolean existsByProfileIdAndPostId(Long profileId, Long postId);

    LookEntity findByProfileIdAndPostId(Long profileId, Long postId);
}

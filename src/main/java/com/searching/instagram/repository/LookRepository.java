package com.searching.instagram.repository;

import com.searching.instagram.entity.LookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LookRepository extends JpaRepository<LookEntity, Long>, JpaSpecificationExecutor<LookEntity> {
    Iterable<LookEntity> findByPostId(Long postId, Pageable pageable);
    Iterable<LookEntity> findByProfileId(Long profileId, Pageable pageable);
}

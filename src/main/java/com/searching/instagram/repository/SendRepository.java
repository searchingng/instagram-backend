package com.searching.instagram.repository;

import com.searching.instagram.entity.SaveEntity;
import com.searching.instagram.entity.SendEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SendRepository extends JpaRepository<SendEntity, Long>, JpaSpecificationExecutor<SendEntity> {
    List<SendEntity> findByPostId(Long postid, Pageable pageable);

    List<SendEntity> findByProfileId(Long profileId, Pageable pageable);

    List<SendEntity> findBySendprofileId(Long sendProfileId, Pageable pageable);
}

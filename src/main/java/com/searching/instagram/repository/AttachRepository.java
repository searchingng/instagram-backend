package com.searching.instagram.repository;

import com.searching.instagram.entity.AttachEntity;
import com.searching.instagram.entity.enums.AttachType;
import org.springframework.aop.aspectj.annotation.NotAnAtAspectException;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttachRepository extends JpaRepository<AttachEntity, String> {

    List<AttachEntity> findByProfileIdAndTypeOrderByCreatedAtDesc(Long prodileId, AttachType type);

}

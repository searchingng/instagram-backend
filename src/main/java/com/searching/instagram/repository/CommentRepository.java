package com.searching.instagram.repository;

import com.searching.instagram.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>, JpaSpecificationExecutor<CommentEntity> {

    //
//    Page<CommentEntity> findAll(Pageable pageable);
    @Query("Select s from CommentEntity s where s.postId=:postId")
    List<CommentEntity> findByPostId(@Param("postId") Long postId);
//
    Optional<CommentEntity> findByContent(String content);

    //
//    @Transactional
//    @Modifying
//    @Query("update CommentEntity p set p.articleId.id=:articleId,p.profileId.id=:profileId,p.content=:content1,p.createdDate=:createdate" +
//            " where p.content=:content")
//    Optional<CommentEntity> update(@Param("articleId") Integer articleId, @Param("profileId") Integer profileId,
//                                   @Param("createdate") LocalDateTime createdate, @Param("content1") String content1, @Param("content") String content);
//
    @Transactional
    @Modifying
    @Query(value = "delete from CommentEntity s where s.content=:content")
    int deleteByContent(@Param("content") String content);

    @Transactional
    @Modifying
    @Query(value = "update CommentEntity p set p.content=:content,p.updatedAt=:updateAt where p.id=:id")
    Optional<CommentEntity> update_Content(@Param("content") String content,
                                           @Param("updateAt") LocalDateTime updateAt, @Param("id") Long id);

    List<CommentEntity> findByProfileId(Long id);
}

package com.searching.instagram.repository;


import com.searching.instagram.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>, JpaSpecificationExecutor<PostEntity> {


    Optional<PostEntity> findByProfileId(Integer profileId);

    Optional<PostEntity> findByContent(String content);

//    @Transactional
//    @Modifying
//    @Query("update PostEntity p set p.title=:title,p.publisher.id=:publishId,p.content=:content,p.createdDate=:createdate," +
//            "p.publishedDate=:pulishdate,p.profile.id=:profileid,p.region.id=:region where p.content=:content")
//    Optional<ArticleEntity> update(@Param("title")String title, @Param("publishId")Integer publisher,
//                                   @Param("createdate") LocalDateTime createdate, @Param("pulishdate")LocalDateTime pulishdate,
//                                   @Param("profileid")Integer profileId, @Param("content")String content, @Param("region")Integer region);
//
//    @Transactional
//    @Modifying
//    @Query(value = "delete from ArticleEntity s where s.content=:content")
//    int deleteByContent(@Param("content") String content);
}

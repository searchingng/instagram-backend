package com.searching.instagram.repository;


import com.searching.instagram.entity.LikeEntity;
import com.searching.instagram.entity.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long>, JpaSpecificationExecutor<LikeEntity> {
    Optional<LikeEntity> findByPostIdAndProfileId(Long id, Long profileid);

    //    @Transactional
//    @Modifying
//    @Query(value = "delete from LikeOrDislikeEntity s where s.id=:id")
//    void deleteById(@Param("id") Integer id);
//
//    @Transactional
//    @Modifying
//    @Query("update LikeOrDislikeEntity  p set p.status=:status, p.profileId=:profileId,p.actionId=:actionId where p.id=:id")
//    Optional<LikeOrDislikeEntity> update(@Param("status") LikeOrDislikeStatus status, @Param("profileId") Integer profileId,
//                                         @Param("actionId") Integer actionId, @Param("id") Integer id);
//
    @Query(value = "select count(s.likeType) from LikeEntity s where s.post.id=:id")
    int findBycountByStatus(@Param("id") Long id);

    List<LikeEntity> findByProfileId(Long profileId, Pageable pageable);

    List<LikeEntity> findByPostId(Long postId, Pageable pageable);

//
//    @Query(value = "select status,count(status) from like_or_dislike where article_id in (select article_id_id from coment where id=:id) group by status",nativeQuery = true)
//    int findBycountByCommentId(@Param("id") Integer id);
//
//    @Query(value = "select * from article where id in (select action_id from like_or_dislike where status='LIKE' and action_status='ARTICLE' and profile_id=:id)",nativeQuery = true)
//    List<ArticleEntity> getprofileLikeArticleList(@Param("id") Integer profileId);
//
//    @Query(value = "select * from coment where id in (select action_id from like_or_dislike where status='LIKE' and action_status='COMMENT' and profile_id=:id))",nativeQuery = true)
//    List<ArticleEntity> getcommetbyprofilelike(@Param("id") Integer profileId);

}

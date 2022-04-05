package com.searching.instagram.repository;


import com.searching.instagram.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByPostId(Long id);

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
//    @Query(value = "select status,count(status) from like_or_dislike where article_id=:id group by status ",nativeQuery = true)
//    int findBycountByStatus(@Param("id") Integer id);
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

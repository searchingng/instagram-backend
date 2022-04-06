package com.searching.instagram.repository;


import com.searching.instagram.entity.LikeEntity;
import com.searching.instagram.entity.PostEntity;
import com.searching.instagram.entity.ProfileEntity;
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

    List<LikeEntity> findByProfileIdOrderByCreatedAt(Long profileId);

    @Query("SELECT l FROM likes l WHERE l.postId = ?1 ORDER BY l.createdAt DESC ")
    List<LikeEntity> findByPostIdOrderByCreatedAtDesc(Long postId);

    int countByPostId(Long postId);

//    Followings Like to post
    /*@Query("SELECT l.profile FROM LikeEntity l INNER JOIN following f " +
            "ON l.profileId = f.profileId WHERE l.postId = ?1 AND f.followerId = ?2")
    List<ProfileEntity> findFollowersLike(Long postId, Long currentUserId);*/

    @Query("SELECT l FROM likes l WHERE l.post.profileId = ?1")
    List<LikeEntity> findByProfilePostsLike(Long profileId);

//    @Query("SELECT l FROM likes l WHERE l.postId = ?1 ORDER BY l.createdAt DESC ")
    List<ProfileEntity> findTop3ByPostIdOrderByCreatedAtDesc(Long postId);

}

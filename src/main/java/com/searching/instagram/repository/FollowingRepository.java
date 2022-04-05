package com.searching.instagram.repository;

import com.searching.instagram.entity.FollowingEntity;
import com.searching.instagram.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowingRepository extends JpaRepository<FollowingEntity, String> {

    @Query("SELECT f.follower FROM following f WHERE f.profileId = ?1")
    List<ProfileEntity> findFollowersById(Long id);

    @Query("SELECT f.profile FROM following f WHERE f.followerId = ?1")
    List<ProfileEntity> findFollowingsById(Long id);

    @Query("SELECT COUNT(f.follower) FROM following f WHERE f.profileId = ?1")
    long countFollowersById(Long id);

    @Query("SELECT COUNT(f.profile) FROM following f WHERE f.followerId = ?1")
    long countFollowingsById(Long id);

    Optional<FollowingEntity> findByFollowerIdAndProfileId(Long followerId, Long profileId);

    void deleteByProfileId(Long profileId);

}

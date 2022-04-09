package com.searching.instagram.repository;

import com.searching.instagram.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long>, JpaSpecificationExecutor<ProfileEntity> {

    Optional<ProfileEntity> findByPhone(String phone);

    Optional<ProfileEntity> findByUsername(String login);

    @Query("SELECT p FROM profile p WHERE p.username like :username")
    List<ProfileEntity> findByUsernameContaining(@Param("username") String username);

    Optional<ProfileEntity> findByUsernameOrPhone(String login, String phone);

    Optional<ProfileEntity> findByUsernameAndPassword(String login, String password);

    boolean existsByPhone(String phone);

    boolean existsByUsername(String username);

}

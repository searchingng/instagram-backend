package com.searching.instagram.repository;

import com.searching.instagram.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    Optional<ProfileEntity> findByPhone(String phone);

    Optional<ProfileEntity> findByUsername(String login);

    Optional<ProfileEntity> findByUsernameOrPhone(String login, String phone);

    Optional<ProfileEntity> findByUsernameAndPassword(String login, String password);

    boolean existsByPhone(String phone);

    boolean existsByUsername(String username);

}

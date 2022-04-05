package com.searching.instagram.service;

import com.searching.instagram.config.security.SecurityUtil;
import com.searching.instagram.dto.FollowingDTO;
import com.searching.instagram.dto.ProfileDTO;
import com.searching.instagram.entity.FollowingEntity;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.exceptions.BadRequestException;
import com.searching.instagram.exceptions.ItemNotFoundException;
import com.searching.instagram.exceptions.UnauthorizedException;
import com.searching.instagram.repository.FollowingRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FollowingService {

    private final FollowingRepository followingRepository;
    private final ProfileService profileService;

    public FollowingDTO toDto(FollowingEntity entity){
        if (entity == null)
            return null;

        FollowingDTO dto = new FollowingDTO();
        dto.setId(entity.getId());
        dto.setFollowerId(entity.getFollowerId());
        dto.setProfileId(entity.getProfileId());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public FollowingEntity get(String id){
        if (id == null){
            return  null;
        }

        return followingRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Following Not Found"));
    }

    public FollowingDTO getById(String id){
        return toDto(get(id));
    }

    public long countFollowersById(Long id){
        return followingRepository.countFollowersById(id);
    }

    public long countFollowingsById(Long id){
        return followingRepository.countFollowingsById(id);
    }

    public List<ProfileDTO> getFollowers(Long id){
        return followingRepository.findFollowersById(id).stream()
                .map(profileService::toDto).collect(Collectors.toList());
    }

    public List<ProfileDTO> getFollowings(Long id){
        return followingRepository.findFollowingsById(id).stream()
                .map(profileService::toDto).collect(Collectors.toList());
    }

    @Transactional
    public FollowingDTO follow(Long id){
        ProfileEntity profile = SecurityUtil.getCurrentUser();
        if (profile == null) {
            log.debug("exception in Get Current User (401 Unauthorized)");
            throw new UnauthorizedException("exception in Get Current User (401 Unauthorized)");
        }

        if (profile.getId() == id){
            throw new BadRequestException("You can't follow yourself");
        }

        FollowingEntity entity = new FollowingEntity();
        entity.setFollowerId(profile.getId());
        entity.setProfileId(id);
        followingRepository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public String unFollow(Long id){
        ProfileEntity profile = SecurityUtil.getCurrentUser();
        if (profile == null) {
            log.debug("exception in Get Current User (401 Unauthorized)");
            throw new UnauthorizedException("exception in Get Current User (401 Unauthorized)");
        }

        try {
            followingRepository.deleteByProfileId(id);
        } catch (RuntimeException e){
            throw new BadRequestException("You are unfollowed to him");
        }

        return "Successfully Unfollowed";
    }

}

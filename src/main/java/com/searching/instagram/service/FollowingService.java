package com.searching.instagram.service;

import com.searching.instagram.dto.FollowingDTO;
import com.searching.instagram.dto.ProfileDTO;
import com.searching.instagram.entity.FollowingEntity;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.exceptions.ItemNotFoundException;
import com.searching.instagram.repository.FollowingRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

}

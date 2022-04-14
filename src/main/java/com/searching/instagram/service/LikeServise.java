package com.searching.instagram.service;


import com.searching.instagram.config.security.SecurityUtil;
import com.searching.instagram.dto.LikeDTO;
import com.searching.instagram.dto.ProfileDTO;
import com.searching.instagram.dto.SaveDTO;
import com.searching.instagram.entity.LikeEntity;
import com.searching.instagram.entity.PostEntity;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.entity.SaveEntity;
import com.searching.instagram.entity.enums.LikeType;
import com.searching.instagram.exceptions.ItemNotFoundException;
import com.searching.instagram.repository.LikeRepository;
import com.searching.instagram.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor

public class LikeServise {
    private final LikeRepository likeRepository;
    private final ProfileService profileService;

    public LikeDTO create(LikeDTO dto) {
        Long profileId = SecurityUtil.getCurrentUser().getId();

        LikeEntity likeEntity = new LikeEntity();
        likeEntity.setPostId(dto.getPostId());
//        likeEntity.setLikeType(LikeType.LIKE);
        likeEntity.setProfileId(profileId);
        likeRepository.save(likeEntity);
        dto.setId(likeEntity.getId());
        dto.setCreated_date(likeEntity.getCreatedAt());
        return dto;

        /*if (entity.get().getLikeType().equals(LikeType.LIKE)) {
//            entity.get().setLikeType(LikeType.EMPTY);
            deleteLikeOrDislike(entity.get().getId());
        }*/
    }

    public void deleteById(Long id) {
        likeRepository.deleteById(id);
    }

    public void deleteAll(){
        likeRepository.deleteAll();
    }

    public void deleteByPostId(Long postId) {
        Long profileId = SecurityUtil.getCurrentUser().getId();
        LikeEntity like = likeRepository.findByPostIdAndProfileId(postId, profileId)
                .orElseThrow(() -> new ItemNotFoundException("You didn't like this post"));

        likeRepository.delete(like);
    }

    public LikeEntity get(Long id) {
        return likeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Like not found"));
    }

    public LikeDTO getById(Long id) {
        return toDTO(get(id));
    }


    public LikeDTO toDTO(LikeEntity entity) {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setId(entity.getId());
//        likeDTO.setLikeType(entity.getLikeType());
        likeDTO.setProfileId(entity.getProfileId());
        likeDTO.setPostId(entity.getPostId());
        likeDTO.setCreated_date(entity.getCreatedAt());
        return likeDTO;
    }

    public Integer getCountPostId(Long postId) {
        int count = likeRepository.countByPostId(postId);
        return count;
    }

    public List<LikeDTO> getAllLikeByMe() {
        Long profileId = SecurityUtil.getCurrentUser().getId();
        return likeRepository.findByProfileIdOrderByCreatedAt(profileId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<LikeDTO> getAllLikeByPostId(Long postId) {
        return likeRepository.findByPostIdOrderByCreatedAtDesc(postId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ProfileDTO> getLast3Likes(Long postId) {
        return likeRepository.findTop3ByPostIdOrderByCreatedAtDesc(postId)
                .stream().map(profileService::toDto).collect(Collectors.toList());
    }

    public List<LikeDTO> getMyPostsLikes(){
        Long currentUserId = SecurityUtil.getCurrentUser().getId();
        return likeRepository.findByProfilePostsLike(currentUserId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }
}

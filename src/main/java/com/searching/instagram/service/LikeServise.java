package com.searching.instagram.service;


import com.searching.instagram.dto.LikeDTO;
import com.searching.instagram.dto.SaveDTO;
import com.searching.instagram.entity.LikeEntity;
import com.searching.instagram.entity.PostEntity;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.entity.SaveEntity;
import com.searching.instagram.entity.enums.LikeType;
import com.searching.instagram.exceptions.ItemNotFoundException;
import com.searching.instagram.repository.LikeRepository;
import com.searching.instagram.repository.ProfileRepository;
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


@Service
public class LikeServise {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentServise commentServise;

    public LikeDTO create(LikeDTO dto) {

        Optional<ProfileEntity> profile = profileRepository.findById(dto.getProfileId());
        PostEntity post = postService.get(dto.getPostId());
        Optional<LikeEntity> entity = likeRepository.findByPostIdAndProfileId(dto.getPostId(), dto.getProfileId());

        if (!entity.isPresent()) {
            LikeEntity likeEntity = new LikeEntity();
            likeEntity.setPost(post);
            likeEntity.setLikeType(LikeType.LIKE);
            likeEntity.setProfile(profile.get());
            likeEntity.setCreatedAt(LocalDateTime.now());
            likeEntity.setUpdatedAt(LocalDateTime.now());
            likeRepository.save(likeEntity);
            dto.setId(entity.get().getId());
            return dto;
        }
        if (entity.get().getLikeType().equals(LikeType.LIKE)) {
//            entity.get().setLikeType(LikeType.EMPTY);
            deleteLikeOrDislike(entity.get().getId());
        }

        likeRepository.save(entity.get());
        dto.setId(entity.get().getId());
        return dto;
    }

    public void deleteLikeOrDislike(Long id) {
        LikeEntity likeEntity = get(id);
        likeRepository.delete(likeEntity);
    }

    public LikeEntity get(Long id) {
        return likeRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Like Or Dislike not found"));
    }


    public LikeDTO toDTO(LikeEntity entity) {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setId(entity.getId());
        likeDTO.setLikeType(entity.getLikeType());
        likeDTO.setProfileId(entity.getProfile().getId());
        likeDTO.setCreated_date(entity.getCreatedAt());
        likeDTO.setPostId(entity.getPost().getId());
        return likeDTO;
    }

    public Integer getCountPostId(Long id) {
        int count = likeRepository.findBycountByStatus(id);
        return count;
    }

    public List<LikeDTO> getAllLikeByProfileId(Long profileId, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<LikeDTO> list = new LinkedList<>();
        List<LikeEntity> entities = likeRepository.findByProfileId(profileId, pageable);
        Iterator<LikeEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public List<LikeDTO> getAllLikeByPostId(Long postId, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<LikeDTO> list = new LinkedList<>();
        List<LikeEntity> entities = likeRepository.findByPostId(postId, pageable);
        Iterator<LikeEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public List<LikeDTO> getAllLike(Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<LikeDTO> list = new LinkedList<>();
        Page<LikeEntity> entities = likeRepository.findAll(pageable);
        Iterator<LikeEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }
}

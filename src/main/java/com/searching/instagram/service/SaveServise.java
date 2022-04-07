package com.searching.instagram.service;

import com.searching.instagram.config.security.SecurityUtil;
import com.searching.instagram.dto.CommentDTO;
import com.searching.instagram.dto.SaveDTO;
import com.searching.instagram.entity.CommentEntity;
import com.searching.instagram.entity.PostEntity;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.entity.SaveEntity;
import com.searching.instagram.entity.enums.ProfileStatus;
import com.searching.instagram.entity.enums.SaveType;
import com.searching.instagram.exceptions.BadRequestException;
import com.searching.instagram.exceptions.ItemNotFoundException;
import com.searching.instagram.repository.ProfileRepository;
import com.searching.instagram.repository.SaveRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SaveServise {

    private final SaveRepository saveRepository;
    private final PostService postService;
    private final ProfileRepository profileRepository;

    public SaveDTO create(SaveDTO dto) {
        PostEntity post = postService.get(dto.getPostId());
        Long profileId = SecurityUtil.getCurrentUser().getId();

        SaveEntity save = new SaveEntity();
        save.setPostId(post.getId());
        save.setProfileId(profileId);
        saveRepository.save(save);
        dto.setId(save.getId());
        return dto;
    }

    public void deleteSave(Long id) {
        Long profileId = SecurityUtil.getCurrentUser().getId();
        Optional<SaveEntity> save = saveRepository.findById(id);

        if (save.isEmpty() || !save.get().getProfileId().equals(profileId)) {
            throw new BadRequestException("This post is not saved");
        }

        saveRepository.delete(save.get());
    }

    public SaveEntity get(Long id) {
        return saveRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item Not Found"));
    }

    public SaveDTO getById(Long id) {
        return toDTO(get(id));
    }

    public SaveDTO toDTO(SaveEntity entity) {
        SaveDTO dto = new SaveDTO();
        dto.setId(entity.getId());
        dto.setCreated_date(entity.getCreatedAt());
        dto.setPostId(entity.getPostId());
        dto.setProfileId(entity.getProfileId());
        return dto;
    }

    public Integer countSavedPostById(Long postId) {
        return saveRepository.countByPostId(postId);
    }

    public List<SaveDTO> getAllSaved() {
        Long profileId = SecurityUtil.getCurrentUser().getId();
        return saveRepository.findByProfileId(profileId).stream()
                .map(this::toDTO).collect(Collectors.toList());

    }
}


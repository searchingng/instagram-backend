package com.searching.instagram.service;

import com.searching.instagram.config.security.SecurityUtil;
import com.searching.instagram.dto.LookDTO;
import com.searching.instagram.entity.LookEntity;
import com.searching.instagram.entity.PostEntity;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.exceptions.ItemNotFoundException;
import com.searching.instagram.repository.LookRepository;
import com.searching.instagram.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LookServise {
    @Autowired
    private LookRepository lookRepository;

    public LookDTO create(LookDTO dto) {
        Long profileId = SecurityUtil.getCurrentUser().getId();

        if (lookRepository.existsByProfileIdAndPostId(profileId, dto.getPostId())){
            LookEntity look = lookRepository.findByProfileIdAndPostId(profileId, dto.getPostId());
            lookRepository.save(look);
        }

        LookEntity lookEntity = new LookEntity();
        lookEntity.setPostId(dto.getPostId());
        lookEntity.setProfileId(profileId);
        lookRepository.save(lookEntity);

        dto.setId(lookEntity.getId());
        dto.setCreated_date(lookEntity.getCreatedAt());
        return dto;
    }

    public LookEntity get(Long id) {
        return lookRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item Not found"));
    }

    public LookDTO getById(Long id) {
        return toDTO(get(id));
    }

    public List<LookDTO> getLookByPostId(Long postId) {
        return lookRepository.findByPostId(postId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public List<LookDTO> getLookByProfileId(Long profileId) {
        return lookRepository.findByProfileId(profileId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }


    public LookDTO toDTO(LookEntity entity) {
        LookDTO lookDTO = new LookDTO();
        lookDTO.setId(entity.getId());
        lookDTO.setPostId(entity.getPostId());
        lookDTO.setProfileId(entity.getProfileId());
        lookDTO.setCreated_date(entity.getCreatedAt());
        return lookDTO;
    }
}

package com.searching.instagram.service;

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

@Service
public class LookServise {
    @Autowired
    private LookRepository lookRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private ProfileRepository profileRepository;

    public LookDTO create(LookDTO dto) {
        PostEntity post = postService.get(dto.getPostId());
        Optional<ProfileEntity> profile = profileRepository.findById(dto.getProfileId());

        LookEntity lookEntity = new LookEntity();
        lookEntity.setPost(post);
        lookEntity.setProfile(profile.get());
        lookEntity.setCreatedAt(LocalDateTime.now());
        lookEntity.setUpdatedAt(LocalDateTime.now());
        lookRepository.save(lookEntity);

        dto.setId(lookEntity.getId());
        dto.setCreated_date(lookEntity.getCreatedAt());
        return dto;
    }

    public LookEntity get(Long id) {
        return lookRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item Not found"));
    }

    public LookDTO getById(Long id) {
        return toDTO(lookRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item Not found")));
    }

    public void deleteLook(Long id) {
        Optional<LookEntity> lookEntity = lookRepository.findById(id);
        lookRepository.delete(lookEntity.get());
    }

    public List<LookDTO> getAllLook(Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<LookDTO> list = new LinkedList<>();
        Iterable<LookEntity> entities = lookRepository.findAll(pageable);
        Iterator<LookEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public List<LookDTO> getLookByPostId(Long postId, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<LookDTO> list = new LinkedList<>();
        Iterable<LookEntity> entities = lookRepository.findByPostId(postId, pageable);
        Iterator<LookEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public List<LookDTO> getLookByProfileId(Long profileId, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<LookDTO> list = new LinkedList<>();
        Iterable<LookEntity> entities = lookRepository.findByProfileId(profileId, pageable);
        Iterator<LookEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }


    public LookDTO toDTO(LookEntity entity) {
        LookDTO lookDTO = new LookDTO();
        lookDTO.setId(entity.getId());
        lookDTO.setPostId(entity.getPost().getId());
        lookDTO.setProfileId(entity.getProfile().getId());
        lookDTO.setCreated_date(entity.getCreatedAt());
        return lookDTO;
    }
}

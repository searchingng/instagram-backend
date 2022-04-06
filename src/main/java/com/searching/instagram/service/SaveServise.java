package com.searching.instagram.service;

import com.searching.instagram.dto.CommentDTO;
import com.searching.instagram.dto.SaveDTO;
import com.searching.instagram.entity.CommentEntity;
import com.searching.instagram.entity.PostEntity;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.entity.SaveEntity;
import com.searching.instagram.entity.enums.ProfileStatus;
import com.searching.instagram.entity.enums.SaveType;
import com.searching.instagram.exceptions.ItemNotFoundException;
import com.searching.instagram.repository.ProfileRepository;
import com.searching.instagram.repository.SaveRepository;
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
public class SaveServise {
    @Autowired
    private SaveRepository saveRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private ProfileRepository profileRepository;

    public SaveDTO create(SaveDTO dto) {
        PostEntity post = postService.get(dto.getPostId());
        Optional<ProfileEntity> profile = profileRepository.findById(dto.getProfileId());

        Optional<SaveEntity> entity = saveRepository.findByProfileIdAndPostId(dto.getProfileId(), dto.getPostId());
        if (!entity.isPresent()) {

            SaveEntity save = new SaveEntity();
            save.setPost(post);
            save.setProfile(profile.get());
            save.setSaveType(SaveType.SAVE);
            save.setCreatedAt(LocalDateTime.now());
            save.setUpdatedAt(LocalDateTime.now());
            saveRepository.save(save);
            dto.setId(save.getId());
            return dto;
        }
        if (entity.get().getSaveType().equals(SaveType.SAVE)) {
//            entity.get().setSaveType(SaveType.NOT_SAVE);
            deleteSave(entity.get().getId());
        }

        saveRepository.save(entity.get());
        dto.setId(entity.get().getId());
        return dto;
    }

    public void deleteSave(Long id) {
        Optional<SaveEntity> save = saveRepository.findById(id);
        saveRepository.delete(save.get());
    }

    public SaveEntity get(Long id) {
        return saveRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item Not Found"));
    }

    public SaveDTO getById(Long id) {
        return toDTO(saveRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item Not Found")));
    }

    public SaveDTO toDTO(SaveEntity entity) {
        SaveDTO dto = new SaveDTO();
        dto.setId(entity.getId());
        dto.setCreated_date(entity.getCreatedAt());
        dto.setPostId(entity.getPost().getId());
        dto.setProfileId(entity.getProfile().getId());
        dto.setSaveType(entity.getSaveType());
        return dto;
    }

    public List<SaveDTO> getAllSaveByPostId(Long postId, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<SaveDTO> list = new LinkedList<>();
        List<SaveEntity> entities = saveRepository.findByPostId(postId, pageable);
        Iterator<SaveEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public List<SaveDTO> getAllSaveByProfileId(Long profileId, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<SaveDTO> list = new LinkedList<>();
        List<SaveEntity> entities = saveRepository.findByProfileId(profileId, pageable);
        Iterator<SaveEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;

    }

    public List<SaveDTO> getAllSave(Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<SaveDTO> list = new LinkedList<>();
        Page<SaveEntity> entities = saveRepository.findAll(pageable);
        Iterator<SaveEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }
}


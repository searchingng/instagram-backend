package com.searching.instagram.service;

import com.searching.instagram.dto.LikeDTO;
import com.searching.instagram.dto.SendDTO;
import com.searching.instagram.entity.LikeEntity;
import com.searching.instagram.entity.PostEntity;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.entity.SendEntity;
import com.searching.instagram.exceptions.ItemNotFoundException;
import com.searching.instagram.repository.ProfileRepository;
import com.searching.instagram.repository.SendRepository;
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
public class SendServise {
    @Autowired
    private SendRepository sendRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private ProfileRepository profileRepository;

    public SendDTO create(SendDTO dto) {
        PostEntity post = postService.get(dto.getPostId());
        Optional<ProfileEntity> profile = profileRepository.findById(dto.getProfileId());
        Optional<ProfileEntity> sendprofile = profileRepository.findById(dto.getSendprofileId());

        SendEntity entity = new SendEntity();
        entity.setPost(post);
        entity.setProfile(profile.get());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setSendprofile(sendprofile.get());
        sendRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public SendDTO getById(Long id) {
        return toDTO(sendRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item Not Found")));
    }

    public SendEntity getBy(Long id) {
        return sendRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item Not Found"));
    }

    public SendDTO toDTO(SendEntity entity) {
        SendDTO sendDTO = new SendDTO();
        sendDTO.setId(entity.getId());
        sendDTO.setProfileId(entity.getProfile().getId());
        sendDTO.setCreated_date(entity.getCreatedAt());
        sendDTO.setPostId(entity.getPost().getId());
        sendDTO.setSendprofileId(entity.getSendprofile().getId());
        return sendDTO;
    }

    public void deleteSend(Long id) {
        SendEntity send = getBy(id);
        sendRepository.delete(send);
    }

    public List<SendDTO> AllSendPost(Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<SendDTO> list = new LinkedList<>();
        Page<SendEntity> entities = sendRepository.findAll(pageable);
        Iterator<SendEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public List<SendDTO> AllSendByPostId(Long postId, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<SendDTO> list = new LinkedList<>();
        List<SendEntity> entities = sendRepository.findByPostId(postId, pageable);
        Iterator<SendEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public List<SendDTO> AllSendByProfileId(Long profileId, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<SendDTO> list = new LinkedList<>();
        List<SendEntity> entities = sendRepository.findByProfileId(profileId, pageable);
        Iterator<SendEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public List<SendDTO> AllSendBySendProfileId(Long sendprofileId, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<SendDTO> list = new LinkedList<>();
        List<SendEntity> entities = sendRepository.findBySendprofileId(sendprofileId, pageable);
        Iterator<SendEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

}

package com.searching.instagram.service;

import com.searching.instagram.config.security.SecurityUtil;
import com.searching.instagram.dto.PostDTO;
import com.searching.instagram.entity.PostEntity;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.exceptions.ItemNotFoundException;
import com.searching.instagram.repository.PostRepository;
import com.searching.instagram.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ProfileRepository profileRepository;

    public PostDTO create(PostDTO dto) {
        ProfileEntity currentUser = SecurityUtil.getCurrentUser();

        PostEntity entity = new PostEntity();
        entity.setContent(dto.getTitle());
        entity.setProfileId(currentUser.getId());
        entity.setLocation(dto.getLocation());
        entity.setHashtag(dto.getHashtag());

        postRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedAt());
        return dto;
    }

    public PostEntity get(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("POST not found"));
    }

    public PostDTO getById(Long id) {
        return toDTO(get(id));
    }

    public List<PostDTO> getAllPost() {
        return postRepository.findAll().stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public void deletePost(Long id) {
        if (id == null)
            return;
        postRepository.deleteById(id);
    }

    public PostDTO toDTO(PostEntity entity) {
        PostDTO dto = new PostDTO();
        dto.setTitle(entity.getContent());
        dto.setProfileId(entity.getProfileId());
        dto.setLocation(entity.getLocation());
        dto.setHashtag(entity.getHashtag());
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedAt());
        return dto;
    }

}

package com.searching.instagram.service;

import com.searching.instagram.config.security.SecurityUtil;
import com.searching.instagram.dto.LookDTO;
import com.searching.instagram.dto.PostDTO;
import com.searching.instagram.entity.AttachEntity;
import com.searching.instagram.entity.PostEntity;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.entity.base.BaseUUIDEntity;
import com.searching.instagram.exceptions.BadRequestException;
import com.searching.instagram.exceptions.ItemNotFoundException;
import com.searching.instagram.repository.PostRepository;
import com.searching.instagram.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AttachService attachService;
    private final LookServise lookServise;

    public PostDTO create(PostDTO dto) {
        ProfileEntity currentUser = SecurityUtil.getCurrentUser();
        Set<AttachEntity> attachs = dto.getAttachs().stream()
                .map(attachService::get).collect(Collectors.toSet());

        PostEntity entity = new PostEntity();
        entity.setContent(dto.getTitle());
        entity.setProfileId(currentUser.getId());
        entity.setLocation(dto.getLocation());
        entity.setHashtag(dto.getHashtag());
        entity.setAttachs(attachs);

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
        try {
            LookDTO view = new LookDTO();
            view.setPostId(id);
            lookServise.create(view);
        } catch (RuntimeException e){

        }
        return toDTO(get(id));
    }

    public List<PostDTO> getAllPost() {
        return postRepository.findAll().stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public void deletePost(Long id) {
        if (id == null)
            return;

        try {
            if (postRepository.existsById(id)) {
                ProfileEntity currentUser = SecurityUtil.getCurrentUser();
                PostEntity post = postRepository.getById(id);
                if (!post.getProfileId().equals(currentUser.getId())){
                    throw new BadRequestException("This is not your post");
                }
                postRepository.deleteById(id);
            }
        } catch (RuntimeException e){
            throw new BadRequestException("Post not found with id = " + id);
        }
    }

    public PostDTO toDTO(PostEntity entity) {
        if (entity == null)
            return null;

        PostDTO dto = new PostDTO();
        dto.setTitle(entity.getContent());
        dto.setProfileId(entity.getProfileId());
        dto.setLocation(entity.getLocation());
        dto.setHashtag(entity.getHashtag());
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedAt());

        Set<String> attachs = entity.getAttachs().stream()
                .map(BaseUUIDEntity::getId).collect(Collectors.toSet());

        dto.setAttachs(attachs);

        return dto;
    }

    public List<PostDTO> getByHashtag(String hashtag) {
        return postRepository.findByHashtagStartsWith(hashtag).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }
}

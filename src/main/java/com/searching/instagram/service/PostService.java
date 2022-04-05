package com.searching.instagram.service;

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

    public PostDTO create(PostDTO dto, Long userId) {
        Optional<ProfileEntity> profile = profileRepository.findById(userId);

        PostEntity entity = new PostEntity();
        entity.setTitle(dto.getTitle());
        entity.setProfile(profile.get());
        entity.setLocation(dto.getLocation());
        // URI ning avtomatik yaratish kerak
        entity.setURI(dto.getURI());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        postRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreated_date(entity.getCreatedAt());
        return dto;
    }

    public PostEntity get(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Article not found"));
    }

    public PostDTO updatePost(PostDTO dto, Long postId) {
//        getProfileByEmail(email);
//        Optional<ProfileEntity> entity = profileRepository.update(dto.getName(), dto.getSurname(),
//                dto.getPswd(), dto.getLogin(), dto.getStatus(), email);
//        ProfileDTO profile = new ProfileDTO();
//        profile.setName(entity.get().getName());
//        profile.setSurname(entity.get().getSurname());
//        profile.setPswd(entity.get().getPswd());
//        profile.setLogin(entity.get().getLogin());
//        profile.setStatus(entity.get().getStatus());
//        profile.setEmail(email);
        PostEntity post = get(postId);
        postRepository.save(post);

        PostDTO dto1 = toDTO(post);
        return dto1;
    }

    public List<PostDTO> getAllPost() {
        List<PostDTO> list = new LinkedList<>();
        Iterable<PostEntity> entities = postRepository.findAll();
        Iterator<PostEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public void deletePost(Long id) {
        PostEntity post = get(id);
        postRepository.delete(post);
    }

    public PostDTO toDTO(PostEntity entity) {
        PostDTO dto = new PostDTO();
        dto.setTitle(entity.getTitle());
        dto.setProfileId(entity.getProfile().getId());
        dto.setLocation(entity.getLocation());
        dto.setURI(entity.getURI());
        dto.setId(entity.getId());
        dto.setCreated_date(entity.getCreatedAt());
        return dto;
    }

}

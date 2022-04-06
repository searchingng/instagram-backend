package com.searching.instagram.service;

import com.searching.instagram.dto.CommentDTO;
import com.searching.instagram.entity.CommentEntity;
import com.searching.instagram.entity.PostEntity;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.exceptions.BadRequestException;
import com.searching.instagram.exceptions.ItemNotFoundException;
import com.searching.instagram.exceptions.UnauthorizedException;
import com.searching.instagram.repository.CommentRepository;
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
public class CommentServise {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PostService postService;

    public CommentDTO create(CommentDTO dto) {
        Optional<ProfileEntity> profile = profileRepository.findById(Long.valueOf(dto.getProfileId()));
        ///post
        PostEntity post = postService.get(dto.getPostId());

        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setProfile(profile.get());
        entity.setPost(post);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        commentRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreated_date(entity.getCreatedAt());
        return dto;
    }

    public CommentEntity getById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Comment not found"));
    }

    public void deleteComment(String content, Long profileId) {
        // Agar kelgan Id Postni yaratgan odamga tegishli bolsa tekshiruvlarsiz ochira olishi kerak

        if (content == null || content.isEmpty()) {
            throw new BadRequestException("Content is empty or equals null");
        }
        Optional<CommentEntity> comment = commentRepository.findById(profileId);
//        shu yerda comment list kelishi mumkin chunki 1 xil komment ko'p bo'lishi mumkin
        if (!comment.get().getContent().equals(content)) {
            throw new UnauthorizedException("the owner of this content was not found");
        }
        commentRepository.deleteByContent(content);
    }


    public CommentDTO updateComment(String content, Integer profileId) {
        if (content == null || content.isEmpty()) {
            throw new BadRequestException("Content not found");
        }
        Optional<CommentEntity> entity1 = commentRepository.findByContent(content);
        if (!entity1.get().getProfile().getId().equals(profileId)) {

            throw new UnauthorizedException("the owner of this content was not found");
        }

        Optional<CommentEntity> entity = commentRepository.update_Content(content, LocalDateTime.now(), entity1.get().getId());

        CommentDTO dto1 = toDTO(entity.get());
        return dto1;
    }

    public List<CommentDTO> getAllComment(Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<CommentDTO> list = new LinkedList<>();
        Iterable<CommentEntity> entities = commentRepository.findAll(pageable);
        Iterator<CommentEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public List<CommentDTO> getProfileCommentList(Integer profileId, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<CommentDTO> list = new LinkedList<>();
        Iterable<CommentEntity> entities = commentRepository.findByProfileId(profileId, pageable);
        Iterator<CommentEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }

    public List<CommentDTO> getPostCommentList(Integer postId, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size, page);
        List<CommentDTO> list = new LinkedList<>();
        Iterable<CommentEntity> entities = commentRepository.findByArticleId(postId, pageable);
        Iterator<CommentEntity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            list.add(toDTO(entityIterator.next()));
        }
        return list;
    }


    public CommentDTO toDTO(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setCreated_date(entity.getCreatedAt());
        dto.setPostId(entity.getPost().getId());
        dto.setProfileId(entity.getProfile().getId());
        return dto;
    }
}

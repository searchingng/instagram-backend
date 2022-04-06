package com.searching.instagram.service;

import com.searching.instagram.config.security.SecurityUtil;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class CommentServise {

    private final CommentRepository commentRepository;
    private final ProfileRepository profileRepository;
    private final PostService postService;

    public CommentDTO create(CommentDTO dto) {
        ///post
        PostEntity post = postService.get(dto.getPostId());
        ProfileEntity profile = SecurityUtil.getCurrentUser();

        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setProfileId(profile.getId());
        entity.setPost(post);

        commentRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreated_date(entity.getCreatedAt());
        return dto;
    }

    public CommentEntity get(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Comment not found"));
    }

    public CommentDTO getById(Long id) {
        return toDTO(get(id));
    }

    public void deleteAll(){
        commentRepository.deleteAll();
    }

    public void deleteComment(Long id) {
        // Agar kelgan Id Postni yaratgan odamga tegishli bolsa tekshiruvlarsiz ochira olishi kerak
        Long profileId = SecurityUtil.getCurrentUser().getId();

        CommentEntity comment = get(id);
//        shu yerda comment list kelishi mumkin chunki 1 xil komment ko'p bo'lishi mumkin
        if (!comment.getProfileId().equals(profileId)) {
            throw new UnauthorizedException("This content is not belonged you");
        }
        commentRepository.deleteById(id);
    }

    /*public CommentDTO updateComment(String content, Integer profileId) {
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
    }*/

    public List<CommentDTO> getAllComment() {
        return commentRepository.findAll().stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public List<CommentDTO> getPostCommentList(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }


    public CommentDTO toDTO(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setCreated_date(entity.getCreatedAt());
        dto.setPostId(entity.getPost().getId());
        dto.setProfileId(entity.getProfileId());
        return dto;
    }
}

package com.searching.instagram.service;


import com.searching.instagram.dto.LikeDTO;
import com.searching.instagram.entity.LikeEntity;
import com.searching.instagram.entity.PostEntity;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.entity.enums.LikeType;
import com.searching.instagram.repository.LikeRepository;
import com.searching.instagram.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class LikeServise {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentServise commentServise;

    public LikeDTO create(LikeDTO dto) {

        Optional<ProfileEntity> profile = profileRepository.findById(dto.getProfileId());

        PostEntity post = postService.get(dto.getPostId());

        Optional<LikeEntity> entity = likeRepository.findByPostId(dto.getPostId());

        if (!entity.isPresent()) {
            LikeEntity likeEntity = new LikeEntity();
            likeEntity.setPost(post);
            likeEntity.setLikeType(LikeType.LIKE);
            likeEntity.setProfile(profile.get());
            likeEntity.setCreatedAt(LocalDateTime.now());
            likeEntity.setUpdatedAt(LocalDateTime.now());
            likeRepository.save(likeEntity);
            dto.setId(entity.get().getId());
            return dto;
        }

        if (entity.get().getLikeType().equals(LikeType.EMPTY)) {
            entity.get().setLikeType(LikeType.LIKE);
        }
        if (entity.get().getLikeType().equals(LikeType.LIKE)) {
            entity.get().setLikeType(LikeType.EMPTY);
        }

        likeOrDislikeRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public void deleteLikeOrDislike(Integer id) {
        get(id);
        likeOrDislikeRepository.deleteById(id);
    }

    public LikeOrDislikeEntity get(Integer id) {
        return likeOrDislikeRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Like Or Dislike not found"));
    }

    public LikeOrDislikeDTO updateArticle(LikeOrDislikeDTO dto, Integer id) {
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
        Optional<LikeOrDislikeEntity> entit = likeOrDislikeRepository.findById(id);
        if (!entit.isPresent()) {
            throw new ItemNotFoundException("LikeOrDislike Not Found");
        }
        Optional<LikeOrDislikeEntity> entity = likeOrDislikeRepository.update(dto.getStatus(), dto.getProfileId(), dto.getActionId(), id);

        LikeOrDislikeDTO dto1 = toDTO(entity.get());
        return dto1;
    }

    public LikeDTO toDTO(LikeOrDislikeEntity entity) {
        LikeDTOLikeOrDislikeRepository dislikeDTO = new LikeOrDislikeDTO();
        dislikeDTO.setId(entity.getId());
        dislikeDTO.setStatus(entity.getStatus());
        dislikeDTO.setProfileId(entity.getProfileId().getId());
        dislikeDTO.setActionId(entity.getActionId());
        dislikeDTO.setCreated_date(entity.getCreatedDate());
        return dislikeDTO;
    }

    public Integer getCountArticleId(Integer id) {
        int count = likeOrDislikeRepository.findBycountByStatus(id);
        return count;
    }

    public Integer getCountByCommentId(Integer id) {
        int count = likeOrDislikeRepository.findBycountByCommentId(id);
        return count;
    }

    public List<ArticleEntity> getarticlebyprofilelike(Integer id) {
        List<ArticleEntity> count = likeOrDislikeRepository.getprofileLikeArticleList(id);
        return count;
    }

    public List<ArticleEntity> getcommetbyprofilelike(Integer id) {
        List<ArticleEntity> count = likeOrDislikeRepository.getprofileLikeArticleList(id);
        return count;
    }
}

package com.searching.instagram.service;

import com.searching.instagram.config.security.SecurityUtil;
import com.searching.instagram.dto.AttachDTO;
import com.searching.instagram.entity.AttachEntity;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.entity.base.BaseUUIDEntity;
import com.searching.instagram.entity.enums.AttachType;
import com.searching.instagram.exceptions.BadRequestException;
import com.searching.instagram.exceptions.ItemNotFoundException;
import com.searching.instagram.repository.AttachRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttachService {

    private final AttachRepository attachRepository;

    @Value("${upload.path}")
    private String parentPath;

    @Value("${server.myhost}")
    private String host;

    public AttachDTO toDto(AttachEntity entity) {
        if (entity == null)
            return null;

        AttachDTO dto = new AttachDTO();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setName(entity.getName());
        dto.setExtension(entity.getExtension());
        dto.setContentType(entity.getContentType());
        dto.setPath(entity.getPath());
        dto.setSize(entity.getSize());
        dto.setType(entity.getType());
        dto.setProfileId(entity.getProfileId());
        dto.setUrl(entity.getUrl());
        return dto;
    }

    public AttachEntity get(String id){
        if (id == null || id.isEmpty()){
            log.debug("Attach not found because id = {}", id);
            return null;
        }

        return attachRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Attach not found"));
    }

    public AttachDTO getById(String id){
        return toDto(get(id));
    }

    public String getExtension(String name) {
        int point = name.lastIndexOf(".");
        return name.substring(point + 1);
    }

    public String getChildPath() {
        LocalDateTime now = LocalDateTime.now();
        return "/" + now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        // "/2022/4/5"
    }
//    @Transactional

    public AttachDTO upload(MultipartFile multipart, String type) {

        String address = parentPath + getChildPath();

        File filePath = new File(address);
        if (!filePath.exists())
            filePath.mkdirs();

        AttachEntity entity = new AttachEntity();
        entity.setName(multipart.getOriginalFilename());
        entity.setSize(multipart.getSize());
        entity.setContentType(multipart.getContentType());
        attachRepository.save(entity);

        String extension = getExtension(entity.getName());
        String path = address + "/" + entity.getId() + "." + extension;
        ProfileEntity currentUser = SecurityUtil.getCurrentUser();
        String url = host + "/attach/open/" + entity.getId();

        entity.setProfileId(currentUser.getId());
        entity.setExtension(extension);
        entity.setUrl(url);
//        entity.setPostId(postId);
        entity.setPath(path);
        entity.setType(AttachType.valueOf(type));
        attachRepository.save(entity);

        try {
            multipart.transferTo(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return toDto(entity);
    }

    public void deleteById(String id){
        AttachEntity attach = get(id);
        File file = new File(attach.getPath());
        if (file.exists()){
            file.delete();
        }
        attachRepository.delete(attach);
    }

    public void deleteAll(){
        List<String> attachs = attachRepository.findAll()
                .stream().map(BaseUUIDEntity::getId).collect(Collectors.toList());

        attachs.stream().forEach(this::deleteById);
    }

    public void delete(AttachEntity attach){
        File file = new File(attach.getPath());
        if (file.exists()){
            file.delete();
        }
        attachRepository.delete(attach);
    }

    public byte[] getForOpen(String id){
        AttachEntity attach = get(id);

        File file = new File(attach.getPath());
        if (file.exists()){
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                return inputStream.readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

    public byte[] getAvatarByProfileId(Long profileId){
        List<AttachEntity> attachs = attachRepository
                .findByProfileIdAndTypeOrderByCreatedAtDesc(profileId, AttachType.AVATAR);

        if (attachs.isEmpty() || attachs == null)
            throw new BadRequestException("This user have not avatar picture");

        AttachEntity attach = attachs.get(0);

        File file = new File(attach.getPath());
        if (file.exists()){
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                return inputStream.readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

    public Resource download(String name) {
        int point = name.indexOf(".");
        String id = name.substring(0, point);
        AttachEntity entity = get(id);

        Path path = Paths.get(entity.getPath());
        try {
            Resource resource = new UrlResource(path.toUri());
            return resource;
        } catch (MalformedURLException e) {
            return download("default");
        }
    }

}

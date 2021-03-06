package com.searching.instagram.service;

import com.searching.instagram.config.security.SecurityUtil;
import com.searching.instagram.dto.ProfileDTO;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.entity.enums.ProfileGender;
import com.searching.instagram.entity.enums.ProfileStatus;
import com.searching.instagram.entity.enums.Role;
import com.searching.instagram.exceptions.BadRequestException;
import com.searching.instagram.exceptions.ItemNotFoundException;
import com.searching.instagram.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final ProfileRepository profileRepository;

    ProfileDTO toDto(ProfileEntity entity){
        if (entity == null)
            return null;

        ProfileDTO dto = new ProfileDTO();
        dto.setFullName(entity.getUsername());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setPhone(entity.getPhone());
        dto.setBirthDate(entity.getBirthDate());
        dto.setEmail(entity.getEmail());
        dto.setBio(entity.getBio());
        dto.setWebsite(entity.getWebsite());

        dto.setGender(entity.getGender());
        dto.setStatus(entity.getStatus());
        dto.setRole(entity.getRole());

        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public ProfileDTO createAdmin(ProfileDTO dto){

        if (profileRepository.existsByPhone(dto.getPhone())){
            log.warn("Phone is already registrated");
            throw new BadRequestException("Phone is already registrated");
        }

        if (profileRepository.existsByUsername(dto.getUsername())){
            log.warn("Username is already registrated");
            throw new BadRequestException("Phone is already registrated");
        }

        String password = DigestUtils.md5Hex(dto.getPassword());
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String password = encoder.encode(dto.getPassword());

        ProfileEntity entity = new ProfileEntity();
        entity.setFullName(dto.getFullName());
        entity.setBio(dto.getBio());
        entity.setWebsite(dto.getWebsite());
        entity.setUsername(dto.getUsername());
        entity.setPassword(password);
        entity.setPhone(dto.getPhone());
        entity.setBirthDate(dto.getBirthDate());
        entity.setEmail(dto.getEmail());

        entity.setGender(dto.getGender());
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setRole(Role.ADMIN_ROLE);

        profileRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setPassword(password);
        log.debug("New ADMIN added: {}", dto);
        return dto;
    }

    public ProfileEntity get(Long id){
        if (id == null){
            return null;
        }
        return profileRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Profile Not Found"));
    }

    public ProfileDTO getById(Long id){
        return toDto(get(id));
    }

    public Page<ProfileDTO> getAll(Pageable pageable){
        return profileRepository.findAll(pageable).map(this::toDto);
    }

    public void deleteById(Long id){
        profileRepository.deleteById(id);
    }

    public void update(ProfileDTO dto){

        ProfileEntity profile = SecurityUtil.getCurrentUser();

        if (dto.getFullName() != null) {
            profile.setFullName(dto.getFullName());
        }

        if (dto.getBio() != null) {
            profile.setBio(dto.getBio());
        }

        if (dto.getPhone() != null) {
            profile.setPhone(dto.getPhone());
        }

        if (dto.getEmail() != null) {
            profile.setEmail(dto.getEmail());
        }

        if (dto.getUsername() != null) {
            profile.setUsername(dto.getUsername());
        }

        if (dto.getBirthDate() != null) {
            profile.setBirthDate(dto.getBirthDate());
        }

        if (dto.getGender() != null) {
            profile.setGender(dto.getGender());
        }

        if (dto.getWebsite() != null) {
            profile.setWebsite(dto.getWebsite());
        }

        if (dto.getPassword() != null) {
            String password = DigestUtils.md5Hex(dto.getPassword());
            profile.setPassword(password);
        }

        profileRepository.save(profile);

    }

    public List<ProfileDTO> getByUsernameContains(String username) {
        if (username == null)
            return new LinkedList<>();
        return profileRepository.findByUsernameContaining("%" + username + "%")
                .stream().map(this::toDto).collect(Collectors.toList());
    }
}

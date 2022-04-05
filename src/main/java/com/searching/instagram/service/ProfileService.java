package com.searching.instagram.service;

import com.searching.instagram.dto.ProfileDTO;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.entity.enums.ProfileGender;
import com.searching.instagram.entity.enums.ProfileStatus;
import com.searching.instagram.entity.enums.Role;
import com.searching.instagram.exceptions.ItemNotFoundException;
import com.searching.instagram.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

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
            throw new RuntimeException("Phone is already registrated");
        }

        if (profileRepository.existsByPhone(dto.getPhone())){
            log.warn("Username is already registrated");
            throw new RuntimeException("Phone is already registrated");
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


}

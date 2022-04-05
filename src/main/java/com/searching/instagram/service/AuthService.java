package com.searching.instagram.service;

import com.searching.instagram.config.jwt.JwtUtil;
import com.searching.instagram.dto.AuthDTO;
import com.searching.instagram.dto.ProfileDTO;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.entity.enums.ProfileStatus;
import com.searching.instagram.entity.enums.Role;
import com.searching.instagram.exceptions.UnauthorizedException;
import com.searching.instagram.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final ProfileRepository profileRepository;

    public ProfileDTO registration(ProfileDTO dto){

        if (profileRepository.existsByPhone(dto.getPhone())){
            log.warn("Phone is already registrated");
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
        entity.setRole(Role.USER_ROLE);

        profileRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setPassword(password);
        log.debug("New User signed up: {}", dto);
        return dto;
    }

    public ProfileDTO login(AuthDTO dto){

        String password = DigestUtils.md5Hex(dto.getPassword());

        ProfileEntity profile = profileRepository
                .findByUsernameOrPhone(dto.getUsername(), dto.getUsername())
                .orElseThrow(() -> {
//                    log.debug("Error in Signing up Username or Password is not valid.");
                    throw new UsernameNotFoundException("Username or Phone is not valid.");
                });

        if (!profile.getPassword().equals(password)){
            throw new UnauthorizedException("Password is wrong!");
        }

        String jwt = JwtUtil.generateJwt(profile.getId(), profile.getUsername());

        ProfileDTO profileDTO = new ProfileDTO();
                profileDTO.setFullName(profile.getFullName());
                profileDTO.setUsername(profile.getUsername());
                profileDTO.setJwt(jwt);

        log.debug("One user signed in: {}", profile);
        return profileDTO;

    }

}

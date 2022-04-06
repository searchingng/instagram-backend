package com.searching.instagram;

import com.searching.instagram.dto.ProfileDTO;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.entity.enums.ProfileGender;
import com.searching.instagram.entity.enums.ProfileStatus;
import com.searching.instagram.entity.enums.Role;
import com.searching.instagram.repository.ProfileRepository;
import com.searching.instagram.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@RequiredArgsConstructor
class InstagramApplicationTests {

//    private final ProfileService profileService;
    private final ProfileRepository profileRepository;

    @Test
    void contextLoads() {

        ProfileDTO dto = new ProfileDTO();
        dto.setUsername("admin");
        dto.setPassword("admin");
        dto.setFullName("Sirojiddin Kilichbaev");
        dto.setBio("ADMIN");
        dto.setBirthDate(LocalDate.of(2004, 5, 1));
        dto.setEmail("ssirojiddin2004@gmail.com");
        dto.setGender(ProfileGender.PREFER_NOT_TO_SAY);
        dto.setPhone("998990000000");
        dto.setRole(Role.ADMIN_ROLE);
        dto.setStatus(ProfileStatus.ACTIVE);
        dto.setWebsite("https://searching-server.herokuapp.com");

//        profileService.createAdmin(dto);

    }

    @Test
    void createAdmin() {

        ProfileEntity entity = new ProfileEntity();
        entity.setUsername("admin");
        entity.setPassword("admin");
        entity.setFullName("Sirojiddin Kilichbaev");
        entity.setBio("ADMIN");
        entity.setBirthDate(LocalDate.of(2004, 5, 1));
        entity.setEmail("ssirojiddin2004@gmail.com");
        entity.setGender(ProfileGender.PREFER_NOT_TO_SAY);
        entity.setPhone("998990000000");
        entity.setRole(Role.ADMIN_ROLE);
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setWebsite("https://searching-server.herokuapp.com");

        profileRepository.save(entity);

    }

}

package com.searching.instagram.controller;

import com.searching.instagram.dto.ProfileDTO;
import com.searching.instagram.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ProfileDTO> createProfile(@RequestBody ProfileDTO dto){
        dto = profileService.createAdmin(dto);
        return ResponseEntity.ok(dto);
    }

}

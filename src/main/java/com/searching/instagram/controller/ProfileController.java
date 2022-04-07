package com.searching.instagram.controller;

import com.searching.instagram.dto.ProfileDTO;
import com.searching.instagram.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<ProfileDTO> createProfile(@RequestBody ProfileDTO dto){
        dto = profileService.createAdmin(dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getOne(@PathVariable("id") Long id){
        return ResponseEntity.ok(profileService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProfileDTO>> getAll(Pageable pageable){
        return ResponseEntity.ok(profileService.getAll(pageable));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id){
        profileService.deleteById(id);
        return ResponseEntity.ok("SUCCESSFULLY DELETED");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> deleteById(@RequestBody ProfileDTO dto){
        profileService.update(dto);
        return ResponseEntity.ok("SUCCESSFULLY SAVED");
    }

}

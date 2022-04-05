package com.searching.instagram.controller;

import com.searching.instagram.dto.FollowingDTO;
import com.searching.instagram.dto.ProfileDTO;
import com.searching.instagram.service.FollowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/following")
@RequiredArgsConstructor
public class FollowingController {

    private final FollowingService followingService;

    @GetMapping("/{id}")
    public ResponseEntity<FollowingDTO> getById(@PathVariable String id){
        return ResponseEntity.ok(followingService.getById(id));
    }

    @GetMapping("/followers/count")
    public ResponseEntity<Long> countFollowersById(Long id){
        return ResponseEntity.ok(followingService.countFollowersById(id));
    }

    @GetMapping("/followings/count")
    public ResponseEntity<Long> countFollowingsById(Long id){
        return ResponseEntity.ok(followingService.countFollowingsById(id));
    }

    @GetMapping("/followers")
    public ResponseEntity<List<ProfileDTO>> getFollowers(Long id){
        return ResponseEntity.ok(followingService.getFollowers(id));
    }

    @GetMapping("/followings")
    public ResponseEntity<List<ProfileDTO>> getFollowings(Long id){
        return ResponseEntity.ok(followingService.getFollowings(id));
    }

}

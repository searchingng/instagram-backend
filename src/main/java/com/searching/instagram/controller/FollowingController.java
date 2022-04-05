package com.searching.instagram.controller;

import com.searching.instagram.dto.FollowingDTO;
import com.searching.instagram.dto.ProfileDTO;
import com.searching.instagram.service.FollowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/followers/count/{id}")
    public ResponseEntity<Long> countFollowersById(@PathVariable Long id){
        return ResponseEntity.ok(followingService.countFollowersById(id));
    }

    @GetMapping("/followings/count/{id}")
    public ResponseEntity<Long> countFollowingsById(@PathVariable Long id){
        return ResponseEntity.ok(followingService.countFollowingsById(id));
    }

    @GetMapping("/followers/{id}")
    public ResponseEntity<List<ProfileDTO>> getFollowers(@PathVariable Long id){
        return ResponseEntity.ok(followingService.getFollowers(id));
    }

    @GetMapping("/followings/{id}")
    public ResponseEntity<List<ProfileDTO>> getFollowings(@PathVariable Long id){
        return ResponseEntity.ok(followingService.getFollowings(id));
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<FollowingDTO> follow(@PathVariable Long id){
        return ResponseEntity.ok(followingService.follow(id));
    }

    @PostMapping("/unfollow/{id}")
    public ResponseEntity<String> unFollow(@PathVariable Long id){
        return ResponseEntity.ok(followingService.unFollow(id));
    }

}

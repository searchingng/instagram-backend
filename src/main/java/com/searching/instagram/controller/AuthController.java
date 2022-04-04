package com.searching.instagram.controller;

import com.searching.instagram.dto.AuthDTO;
import com.searching.instagram.dto.ProfileDTO;
import com.searching.instagram.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<ProfileDTO> registration(@RequestBody ProfileDTO dto){

        return ResponseEntity.ok(authService.registration(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthDTO dto){

        return ResponseEntity.ok(authService.login(dto));
    }

}

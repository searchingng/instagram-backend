package com.searching.instagram.controller;


import com.searching.instagram.config.jwt.JwtDTO;
import com.searching.instagram.config.jwt.JwtUtil;
import com.searching.instagram.config.security.SecurityUtil;
import com.searching.instagram.dto.PostDTO;
import com.searching.instagram.entity.PostEntity;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.entity.enums.Role;
import com.searching.instagram.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
@Api(tags = "post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    @ApiOperation(value = "create method", notes = "Bunda post create qilinadi faqat Adminlar ", nickname = "NickName")
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostDTO dto) {
        PostDTO response = postService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "Delete method", notes = "Bunda  post Delete qilinadi content orqali  faqat Adminlar ", nickname = "NickName")
    public String DeletePostByAdmin(@PathVariable("id") Long postId) {
        postService.deletePost(postId);
        return "Succesfully";
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get Article By Id method", notes = "Bunda bor post ni olish uchun ishlatiladi Id orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getArticleById(@PathVariable("id") Long id) {
        PostDTO response = postService.getById(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    @ApiOperation(value = "Aricle List method", notes = "Bunda bor postlar Olinadi  Userlar  ", nickname = "NickName")
    public ResponseEntity<List<PostDTO>> getArticleList() {
        List<PostDTO> list = postService.getAllPost();
        return ResponseEntity.ok(list);
    }


}

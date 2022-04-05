package com.searching.instagram.controller;


import com.searching.instagram.config.jwt.JwtDTO;
import com.searching.instagram.config.jwt.JwtUtil;
import com.searching.instagram.dto.PostDTO;
import com.searching.instagram.entity.PostEntity;
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
@RequestMapping("/article")
@Api(tags = "article")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    @ApiOperation(value = "create method", notes = "Bunda article create qilinadi faqat Adminlar ", nickname = "NickName")
    public ResponseEntity<?> create(HttpServletRequest request, @Valid @RequestBody PostDTO dto) {
        JwtDTO jwtDTO = JwtUtil.getProfile(request, Role.ADMIN_ROLE);
        PostDTO response = postService.create(dto, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/{id}")
    @ApiOperation(value = "update method", notes = "Bunda article update qilinadi content orqali  faqat Adminlar ", nickname = "NickName")
    public ResponseEntity<PostDTO> updatePostByAdmin(HttpServletRequest request,
                                                     @PathVariable("id") Long id, @Valid @RequestBody PostDTO dto) {
        PostDTO entity = postService.updatePost(dto, id);
        return ResponseEntity.ok(entity);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "Delete method", notes = "Bunda  article Delete qilinadi content orqali  faqat Adminlar ", nickname = "NickName")
    public String DeletePostByAdmin(HttpServletRequest request, @PathVariable("id") Long postId) {
        postService.deletePost(postId);
        return "Succesfully";
    }

    @GetMapping("/getarticlebyid/{id}")
    @ApiOperation(value = "get Article By Id method", notes = "Bunda bor article ni olish uchun ishlatiladi Id orqali faqat Admin lar ", nickname = "NickName")
    public ResponseEntity<?> getArticleById(HttpServletRequest request, @PathVariable("id") Long id) {
        PostEntity response = postService.get(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/allarticle")
    @ApiOperation(value = "Aricle List method", notes = "Bunda bor articlelar Olinadi  Userlar  ", nickname = "NickName")
    public ResponseEntity<?> getArticleList(HttpServletRequest request) {
        List<PostDTO> list = postService.getAllPost();
        return ResponseEntity.ok(list);
    }


}

package com.searching.instagram.controller;

import com.searching.instagram.config.jwt.JwtDTO;
import com.searching.instagram.config.jwt.JwtUtil;
import com.searching.instagram.dto.CommentDTO;
import com.searching.instagram.entity.CommentEntity;
import com.searching.instagram.service.CommentServise;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
@Api(tags = "comment")
public class CommentController {
    @Autowired
    private CommentServise commentServise;

    @PostMapping("/create")
    @ApiOperation(value = "create  method", notes = "Bunda comment create qilinadi", nickname = "NickName")
    public ResponseEntity<?> create(@Valid @RequestBody CommentDTO dto, HttpServletRequest request) {
        CommentDTO dto1 = commentServise.create(dto);
        return ResponseEntity.ok(dto1);

    }

    @GetMapping("/getarticlebyid/{id}")
    @ApiOperation(value = "get Comment By Id  method", notes = "Bunda comment olinadi faqat Statusi publish bo'lgan Articlelardan ", nickname = "NickName")
    public ResponseEntity<?> getCommentById(HttpServletRequest request, @PathVariable Long id) {
        CommentEntity response = commentServise.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/{content}/{id}")
    @ApiOperation(value = "update  method", notes = "Bunda comment update qilinadi content orqali faqat adminlar ", nickname = "NickName")
    public ResponseEntity<CommentDTO> updateCommentByAdmin(@PathVariable("content") String content, @PathVariable("id") Integer id) {
        CommentDTO entity = commentServise.updateComment(content, id);
        return ResponseEntity.ok(entity);
    }

    @GetMapping("/delete/{content}/{id}")
    @ApiOperation(value = "Delete method", notes = "Bunda comment delete qilinadi content orqali user lar o'zi yozgan commentlarni ", nickname = "NickName")
    public String DeleteCommentByAdmin(@PathVariable("content") String content, @PathVariable("id") Long id) {
        commentServise.deleteComment(content, id);
        return "Succesfully";
    }


    @GetMapping("/allcomment")
    @ApiOperation(value = "getCommentList method", notes = "Bunda commentlar olinadi faqat Adminlar tomonidan", nickname = "NickName")
    public ResponseEntity<?> getCommentList(@RequestParam("page") int page, @RequestParam("size") int size) {
        List<CommentDTO> list = commentServise.getAllComment(size, page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getprofilecommentlist/{profileid}")
    @ApiOperation(value = "getProfileCommentList method", notes = "Bunda profile yozgan barcha commentlar olinadi pagenition bilan", nickname = "NickName")
    public ResponseEntity<?> getProfileCommentList(@PathVariable("profileid") Integer profiled, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<CommentDTO> list = commentServise.getProfileCommentList(profiled, size, page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getarticlrcommentlist/{postid}")
    @ApiOperation(value = "GetArticleCommentList method", notes = "Bunda Article ga yozilgan Commentlar olinadi pagenition bilan", nickname = "NickName")
    public ResponseEntity<?> getArticleCommentList(@PathVariable("postid") Integer postid, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<CommentDTO> list = commentServise.getPostCommentList(postid, size, page);
        return ResponseEntity.ok(list);
    }


}

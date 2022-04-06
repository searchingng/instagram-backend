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

    @PostMapping()
    @ApiOperation(value = "create  method", notes = "Bunda comment create qilinadi", nickname = "NickName")
    public ResponseEntity<CommentDTO> create(@Valid @RequestBody CommentDTO dto) {
        dto = commentServise.create(dto);
        return ResponseEntity.ok(dto);

    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get Comment By Id  method", notes = "Bunda comment olinadi with ID ", nickname = "NickName")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable("id") Long id) {
        CommentDTO response = commentServise.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "Delete method", notes = "Bunda comment delete qilinadi content orqali user lar o'zi yozgan commentlarni ", nickname = "NickName")
    public String DeleteCommentByAdmin(@PathVariable("id") Long id) {
        commentServise.deleteComment(id);
        return "Succesfully Deleted";
    }


    @GetMapping
    @ApiOperation(value = "getCommentList method", notes = "Bunda commentlar olinadi faqat Adminlar tomonidan", nickname = "NickName")
    public ResponseEntity<List<CommentDTO>> getCommentList() {
        List<CommentDTO> list = commentServise.getAllComment();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/post/{postId}")
    @ApiOperation(value = "GetArticleCommentList method", notes = "Bunda Article ga yozilgan Commentlar olinadi pagenition bilan", nickname = "NickName")
    public ResponseEntity<List<CommentDTO>> getArticleCommentList(@PathVariable("postId") Integer postId) {
        List<CommentDTO> list = commentServise.getPostCommentList(postId);
        return ResponseEntity.ok(list);
    }


}

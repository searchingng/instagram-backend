package com.searching.instagram.controller;

import com.searching.instagram.dto.LikeDTO;
import com.searching.instagram.dto.ProfileDTO;
import com.searching.instagram.service.LikeServise;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/like")
@Api(tags = "like")
@CrossOrigin
public class LikeController {
    @Autowired
    private LikeServise likeServise;

    @PostMapping
    @ApiOperation(value = "create method ", notes = "Bunda Like create qilinadi ", nickname = "NickName")
    public ResponseEntity<LikeDTO> create(@Valid @RequestBody LikeDTO dto) {
        LikeDTO response = likeServise.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/delete/{postId}")
    @ApiOperation(value = "Delete method ", notes = "Bunda Like delete qilinadi postId bo'yicha ", nickname = "NickName")
    public String DeleteLikeOrDislike( @PathVariable("postId") Long postId) {
        likeServise.deleteByPostId(postId);
        return "Succesfully Deleted";
    }

    //    Get article like and dislike count
    @GetMapping("/post/count/{id}")
    @ApiOperation(value = "GetCountByArticleId method ", notes = "Bunda Like soni olinadi article id orqali ", nickname = "NickName")
    public ResponseEntity<Integer> getCountByArticle_Id(@PathVariable("id") Long id) {
        Integer count = likeServise.getCountPostId(id);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/me")
    @ApiOperation(value = "get all likes of my posts method ", notes = "Bunda CurrentUser postlarining barcha Like lar olinadi ", nickname = "NickName")
    public ResponseEntity<List<LikeDTO>> getMyPostsLikes() {
        List<LikeDTO> list = likeServise.getMyPostsLikes();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/my")
    @ApiOperation(value = "get my liker to others method ", notes = "Bunda CurrentUser bosgan hamma likelar olinadi ", nickname = "NickName")
    public ResponseEntity<List<LikeDTO>> getAllLikeByProfileId() {
        List<LikeDTO> list = likeServise.getAllLikeByMe();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/post/{id}")
    @ApiOperation(value = "getAllLikeByPostId method ", notes = "Bunda barcha Like lar olinadi PostId orqali ", nickname = "NickName")
    public ResponseEntity<List<LikeDTO>> getAllLikeByPostId(@PathVariable("id") Long id) {
        List<LikeDTO> list = likeServise.getAllLikeByPostId(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/post/last/{id}")
    @ApiOperation(value = "get last 3 liker method ", notes = "Bunda barcha Like lar olinadi ProfileId orqali ", nickname = "NickName")
    public ResponseEntity<List<ProfileDTO>> getLast3Likes(@PathVariable("id") Long id) {
        List<ProfileDTO> list = likeServise.getLast3Likes(id);
        return ResponseEntity.ok(list);
    }
}

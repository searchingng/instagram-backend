package com.searching.instagram.controller;

import com.searching.instagram.dto.LikeDTO;
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
@RequestMapping("/Like")
@Api(tags = "like")
public class LikeController {
    @Autowired
    private LikeServise likeServise;

    @PostMapping("")
    @ApiOperation(value = "create method ",notes = "Bunda Like yoki Dislike create,update,delete qilinadi qilinadi ",nickname = "NickName")
    public ResponseEntity<?> create(@Valid @RequestBody LikeDTO dto,
                                    HttpServletRequest request) {
        System.out.println(request.getAttribute("jwtDTO"));
        LikeDTO response = likeServise.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "Delete method ",notes = "Bunda Like yoki Dislike delete qilinadi id bo'yicha ",nickname = "NickName")
    public String DeleteLikeOrDislike(HttpServletRequest request, @PathVariable("id") Long id) {
        likeServise.deleteLikeOrDislike(id);
        return "Succesfully";
    }

    //    Get article like and dislike count
    @GetMapping("/getcount/{id}")
    @ApiOperation(value = "GetCountByArticleId method ",notes = "Bunda Like yoki Dislike soni olinadi article id orqali ",nickname = "NickName")
    public ResponseEntity<?> getCountByArticle_Id(@PathVariable("id") Long id) {
        Integer count = likeServise.getCountPostId(id);
        return ResponseEntity.ok(count);
    }
//    @GetMapping("/getcountbycommentid/{id}")
//    @ApiOperation(value = "GetCountByCommentId method ",notes = "Bunda Like yoki Dislike soni olinadi comment id orqali ",nickname = "NickName")
//    public ResponseEntity<?> getCountByCommentId(@PathVariable("id") Integer id) {
//        Integer count = likeOrDislikeServise.getCountByCommentId(id);
//        return ResponseEntity.ok(count);
//    }
//
//    @GetMapping("/getarticlebyprofilelike/{id}")
//    @ApiOperation(value = "getarticlebyprofilelike method ",notes = "Bunda ayni bir profile like bosgan article lar",nickname = "NickName")
//    public ResponseEntity<?> getarticlebyprofilelike(HttpServletRequest request,@PathVariable("id") Integer id) {
//        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
//        List<ArticleEntity> count = likeOrDislikeServise.getarticlebyprofilelike(id);
//        return ResponseEntity.ok(count);
//    }
//    @GetMapping("/getcommetbyprofilelike/{id}")
//    @ApiOperation(value = "getcommetbyprofilelike method ",notes = "Bunda ayni bir profile like bosgan commentlar lar",nickname = "NickName")
//    public ResponseEntity<?> getcommetbyprofilelike(HttpServletRequest request,@PathVariable("id") Integer id) {
//        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
//        List<ArticleEntity> count = likeOrDislikeServise.getcommetbyprofilelike(id);
//        return ResponseEntity.ok(count);
//    }
}

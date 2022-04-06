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

    @PostMapping("create")
    @ApiOperation(value = "create method ", notes = "Bunda Like yoki Dislike create,update,delete qilinadi qilinadi ", nickname = "NickName")
    public ResponseEntity<?> create(@Valid @RequestBody LikeDTO dto) {
        LikeDTO response = likeServise.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "Delete method ", notes = "Bunda Like yoki Dislike delete qilinadi id bo'yicha ", nickname = "NickName")
    public String DeleteLikeOrDislike( @PathVariable("id") Long id) {
        likeServise.deleteLikeOrDislike(id);
        return "Succesfully";
    }

    //    Get article like and dislike count
    @GetMapping("/getcount/{id}")
    @ApiOperation(value = "GetCountByArticleId method ", notes = "Bunda Like yoki Dislike soni olinadi article id orqali ", nickname = "NickName")
    public ResponseEntity<?> getCountByArticle_Id(@PathVariable("id") Long id) {
        Integer count = likeServise.getCountPostId(id);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/getAllLike")
    @ApiOperation(value = "GetAllLike method ", notes = "Bunda barcha Like lar olinadi ", nickname = "NickName")
    public ResponseEntity<?> getAllLike(@RequestParam("page") int page, @RequestParam("size") int size) {
        List<LikeDTO> list = likeServise.getAllLike(size, page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getAllLikeByPostId/{id}")
    @ApiOperation(value = "getAllLikeByPostId method ", notes = "Bunda barcha Like lar olinadi PostId orqali ", nickname = "NickName")
    public ResponseEntity<?> getAllLikeByPostId(@PathVariable("id") Long id, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<LikeDTO> list = likeServise.getAllLikeByPostId(id, size, page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getAllLikeByProfileId/{id}")
    @ApiOperation(value = "getAllLikeByProfileId method ", notes = "Bunda barcha Like lar olinadi ProfileId orqali ", nickname = "NickName")
    public ResponseEntity<?> getAllLikeByProfileId(@PathVariable("id") Long id, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<LikeDTO> list = likeServise.getAllLikeByProfileId(id, size, page);
        return ResponseEntity.ok(list);
    }
}

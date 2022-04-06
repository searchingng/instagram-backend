package com.searching.instagram.controller;

import com.searching.instagram.dto.LookDTO;
import com.searching.instagram.service.LookServise;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Look")
@Api(tags = "Look")
public class LookController {
    @Autowired
    private LookServise lookServise;


    @PostMapping("create")
    @ApiOperation(value = "create method ", notes = "Bunda Look create qilinadi ", nickname = "NickName")
    public ResponseEntity<?> create(@Valid @RequestBody LookDTO dto) {
        LookDTO response = lookServise.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getLook/{id}")
    @ApiOperation(value = "GetLook method ", notes = "Bunda look olinadi id orqali ", nickname = "NickName")
    public ResponseEntity<?> getLookById(@PathVariable("id") Long id) {
        LookDTO lookDTO = lookServise.getById(id);
        return ResponseEntity.ok(lookDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete method ", notes = "Bunda Look delete qilinadi id bo'yicha ", nickname = "NickName")
    public String DeleteLike(@PathVariable("id") Long id) {
        lookServise.deleteLook(id);
        return "Succesfully";
    }

    @GetMapping("/allLook")
    @ApiOperation(value = "getAllLook method", notes = "Bunda Looklar olinadi ", nickname = "NickName")
    public ResponseEntity<?> getAllLook(@RequestParam("page") int page, @RequestParam("size") int size) {
        List<LookDTO> list = lookServise.getAllLook(size, page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/allLookByPostId/{postId}")
    @ApiOperation(value = "getAllLookByPostId method", notes = "Bunda Looklar postId bo'yicha olinadi ", nickname = "NickName")
    public ResponseEntity<?> getLookByPostId(@PathVariable("postId") Long postId, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<LookDTO> list = lookServise.getLookByPostId(postId, size, page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/allLookByProfileId/{profileId}")
    @ApiOperation(value = "getAllLookByProfileId method", notes = "Bunda Looklar profileId orqali olinadi ", nickname = "NickName")
    public ResponseEntity<?> getLookByProfileId(@PathVariable("profileId") Long profileId, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<LookDTO> list = lookServise.getLookByProfileId(profileId, size, page);
        return ResponseEntity.ok(list);
    }

}

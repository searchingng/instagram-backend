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
@RequestMapping("/look")
@Api(tags = "Look")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LookController {
    @Autowired
    private LookServise lookServise;


    @PostMapping
    @ApiOperation(value = "create method ", notes = "Bunda Look create qilinadi ", nickname = "NickName")
    public ResponseEntity<LookDTO> create(@Valid @RequestBody LookDTO dto) {
        LookDTO response = lookServise.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "GetLook method ", notes = "Bunda look olinadi id orqali ", nickname = "NickName")
    public ResponseEntity<LookDTO> getLookById(@PathVariable("id") Long id) {
        LookDTO lookDTO = lookServise.getById(id);
        return ResponseEntity.ok(lookDTO);
    }

    @GetMapping("/post/{postId}")
    @ApiOperation(value = "getAllLookByPostId method", notes = "Bunda Looklar postId bo'yicha olinadi ", nickname = "NickName")
    public ResponseEntity<List<LookDTO>> getLookByPostId(@PathVariable("postId") Long postId) {
        List<LookDTO> list = lookServise.getLookByPostId(postId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/profile/{profileId}")
    @ApiOperation(value = "getAllLookByProfileId method", notes = "Bunda Looklar profileId orqali olinadi ", nickname = "NickName")
    public ResponseEntity<List<LookDTO>> getLookByProfileId(@PathVariable("profileId") Long profileId) {
        List<LookDTO> list = lookServise.getLookByProfileId(profileId);
        return ResponseEntity.ok(list);
    }

}

package com.searching.instagram.controller;

import com.searching.instagram.dto.LikeDTO;
import com.searching.instagram.dto.SaveDTO;
import com.searching.instagram.entity.CommentEntity;
import com.searching.instagram.service.SaveServise;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/save")
@Api(tags = "save")
public class SaveController {
    @Autowired
    private SaveServise saveServise;

    @PostMapping
    @ApiOperation(value = "create method ", notes = "Bunda save create,update,delete qilinadi qilinadi ", nickname = "NickName")
    public ResponseEntity<?> create(@Valid @RequestBody SaveDTO dto) {
        SaveDTO response = saveServise.create(dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete method ", notes = "Bunda Save delete qilinadi id bo'yicha ", nickname = "NickName")
    public String DeleteSave(@PathVariable("id") Long id) {
        saveServise.deleteSave(id);
        return "Succesfully Deleted";
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get Save By Id  method", notes = "get By id", nickname = "NickName")
    public ResponseEntity<?> getSaveById(@PathVariable("id") Long id) {
        SaveDTO response = saveServise.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/post/count/{id}")
    @ApiOperation(value = "getCountSaveByPostId method ", notes = "Bunda barcha Save lar soni olinadi PostId orqali ", nickname = "NickName")
    public ResponseEntity<Integer> countAllByPostId(@PathVariable("id") Long id) {
        Integer count = saveServise.countSavedPostById(id);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/my")
    @ApiOperation(value = "getAllSaveByProfileId method ", notes = "Bunda barcha Save lar olinadi CurrentUser orqali ", nickname = "NickName")
    public ResponseEntity<List<SaveDTO>> getAllSaveByProfileId() {
        List<SaveDTO> list = saveServise.getAllSaved();
        return ResponseEntity.ok(list);
    }
}

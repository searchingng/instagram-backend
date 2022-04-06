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

    @PostMapping("create")
    @ApiOperation(value = "create method ", notes = "Bunda save create,update,delete qilinadi qilinadi ", nickname = "NickName")
    public ResponseEntity<?> create(@Valid @RequestBody SaveDTO dto) {
        SaveDTO response = saveServise.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "Delete method ", notes = "Bunda Save delete qilinadi id bo'yicha ", nickname = "NickName")
    public String DeleteSave(@PathVariable("id") Long id) {
        saveServise.deleteSave(id);
        return "Succesfully";
    }

    @GetMapping("/getSave/{id}")
    @ApiOperation(value = "get Save By Id  method", notes = "Bunda Save olinadi faqat Statusi publish bo'lgan Articlelardan ", nickname = "NickName")
    public ResponseEntity<?> getSaveById(@PathVariable("id") Long id) {
        SaveDTO response = saveServise.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllSaveByPostId/{id}")
    @ApiOperation(value = "getAllSaveByPostId method ", notes = "Bunda barcha Save lar olinadi PostId orqali ", nickname = "NickName")
    public ResponseEntity<?> getAllLikeByPostId(@PathVariable("id") Long id, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<SaveDTO> list = saveServise.getAllSaveByPostId(id, size, page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getAllSaveByProfileId/{id}")
    @ApiOperation(value = "getAllSaveByProfileId method ", notes = "Bunda barcha Save lar olinadi ProfileId orqali ", nickname = "NickName")
    public ResponseEntity<?> getAllSaveByProfileId(@PathVariable("id") Long id, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<SaveDTO> list = saveServise.getAllSaveByProfileId(id, size, page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getAllSave")
    @ApiOperation(value = "getAllSave method ", notes = "Bunda barcha Save lar olinadi ", nickname = "NickName")
    public ResponseEntity<?> getAllSave(@RequestParam("page") int page, @RequestParam("size") int size) {
        List<SaveDTO> list = saveServise.getAllSave(size, page);
        return ResponseEntity.ok(list);
    }
}

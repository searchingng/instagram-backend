package com.searching.instagram.controller;

import com.searching.instagram.dto.CommentDTO;
import com.searching.instagram.dto.LookDTO;
import com.searching.instagram.dto.SaveDTO;
import com.searching.instagram.dto.SendDTO;
import com.searching.instagram.service.SendServise;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/send")
@Api(tags = "send")
public class SendController {
    @Autowired
    private SendServise sendServise;

    @PostMapping("create")
    @ApiOperation(value = "create method ", notes = "Bunda send create qilinadi ", nickname = "NickName")
    public ResponseEntity<?> create(@Valid @RequestBody SendDTO dto) {

        SendDTO response = sendServise.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getSend/{id}")
    @ApiOperation(value = "GetSend method ", notes = "Bunda send olinadi id orqali ", nickname = "NickName")
    public ResponseEntity<?> getSendById(@PathVariable("id") Long id) {
        SendDTO sendDTO = sendServise.getById(id);
        return ResponseEntity.ok(sendDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete method ", notes = "Bunda send delete qilinadi id bo'yicha ", nickname = "NickName")
    public String DeleteLike(@PathVariable("id") Long id) {
        sendServise.deleteSend(id);
        return "Succesfully";
    }

    @GetMapping("/AllSendPost")
    @ApiOperation(value = "AllSendPost method", notes = "Bunda barcha Send bo'lgan postlar olinadi pagenition bilan", nickname = "NickName")
    public ResponseEntity<?> AllSendPost(@RequestParam("page") int page, @RequestParam("size") int size) {
        List<SendDTO> list = sendServise.AllSendPost(size, page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/AllSendByPostId/{postId}")
    @ApiOperation(value = "AllSendByPostId method", notes = "Bunda barcha yuborilgan postlar olinadi pagenition bilan", nickname = "NickName")
    public ResponseEntity<?> AllSendByPostId(@PathVariable("postId") Long postId, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<SendDTO> list = sendServise.AllSendByPostId(postId, size, page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/AllSendByProfileId/{profileId}")
    @ApiOperation(value = "AllSendByProfileId method", notes = "Bunda shu profileId yuborgan postlar olinadi pagenition bilan", nickname = "NickName")
    public ResponseEntity<?> AllSendByProfileId(@PathVariable("profileId") Long profileId, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<SendDTO> list = sendServise.AllSendByProfileId(profileId, size, page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/AllSendBySendProfileId/{profileId}")
    @ApiOperation(value = "AllSendBySendProfileId method", notes = "Bunda shu profileIdga yuborilgan postlar olinadi pagenition bilan", nickname = "NickName")
    public ResponseEntity<?> AllSendBySendProfileId(@PathVariable("profileId") Long profileId, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<SendDTO> list = sendServise.AllSendBySendProfileId(profileId, size, page);
        return ResponseEntity.ok(list);
    }
}

package com.searching.instagram.controller;

import com.searching.instagram.dto.AttachDTO;
import com.searching.instagram.entity.AttachEntity;
import com.searching.instagram.service.AttachService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attach")
@RequiredArgsConstructor
@Api(tags = "attach")
public class AttachController {

    private final AttachService attachService;

    @PostMapping("/upload/{type}")
    public ResponseEntity<AttachDTO> uplaod(@RequestParam("file") MultipartFile multipartFile,
                                            @PathVariable("type") String type){
        return ResponseEntity.ok(attachService.upload(multipartFile, type));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AttachDTO> getById(@PathVariable String id){
        return ResponseEntity.ok(attachService.getById(id));
    }

    @GetMapping(value = "/open/{id}", produces = {"image/*", MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> open(@PathVariable String id){
        return ResponseEntity.ok(attachService.getForOpen(id));
    }

}

package com.searching.instagram.controller;

import com.searching.instagram.dto.AttachDTO;
import com.searching.instagram.entity.AttachEntity;
import com.searching.instagram.service.AttachService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attach")
@RequiredArgsConstructor
@Api(tags = "attach")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AttachController {

    private final AttachService attachService;

    @PostMapping("/upload/{type}")
    public ResponseEntity<AttachDTO> uplaod(@RequestParam("file") MultipartFile multipartFile,
                                            @PathVariable("type") String type){
        return ResponseEntity.ok(attachService.upload(multipartFile, type));
    }

    /*@PostMapping("/upload/post/{postId}")
    public ResponseEntity<AttachDTO> uplaodForPost(@RequestParam("file") MultipartFile multipartFile,
                                                   @PathVariable("postId") Long postId){
        return ResponseEntity.ok(attachService.upload(multipartFile, postId, AttachType.POST));
    }

    @PostMapping("/upload/avatar")
    public ResponseEntity<AttachDTO> uplaodForAvatar(@RequestParam("file") MultipartFile multipartFile,
                                            @PathVariable("type") String type){
        return ResponseEntity.ok(attachService.upload(multipartFile, null, AttachType.AVATAR));
    }*/

    @GetMapping("/get/{id}")
    public ResponseEntity<AttachDTO> getById(@PathVariable String id){
        return ResponseEntity.ok(attachService.getById(id));
    }

    @GetMapping(value = "/open/{id}", produces = {"image/*", MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> open(@PathVariable String id){
        return ResponseEntity.ok(attachService.getForOpen(id));
    }

    @GetMapping(value = "/download/{fileName:.+}")
    public ResponseEntity<Resource> download(@PathVariable("fileName") String name){
        Resource resource = attachService.download(name);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; fileName=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping(value = "/avatar/{profileId}", produces = {"image/*", MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> getAvatar(@PathVariable("profileId") Long id){
        return ResponseEntity.ok(attachService.getAvatarByProfileId(id));
    }

}

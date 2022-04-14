package com.searching.instagram.controller;

import com.searching.instagram.service.AdminService;
import com.searching.instagram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @DeleteMapping("/delete")
    public void delete(){
        adminService.delete();
    }

}

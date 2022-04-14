package com.searching.instagram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final PostService postService;
    private final AttachService attachService;
    private final LookServise lookServise;
    private final SaveServise saveServise;
    private final LikeServise likeServise;
    private final CommentServise commentServise;

    public void delete() {
        lookServise.deleteAll();
        likeServise.deleteAll();
        attachService.deleteAll();
        saveServise.deleteAll();
        commentServise.deleteAll();
        postService.deleteAll();
    }
}

package com.searching.instagram.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FollowingDTO {

    private String id;
    private Long followerId;
    private Long profileId;
    private LocalDateTime createdAt;

}

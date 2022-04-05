package com.searching.instagram.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.searching.instagram.entity.enums.AttachType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {

    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String extension;
    private String contentType;
    private String path;
    private Long size;
    private AttachType type;

    private Long profileId;
    private String url;

}

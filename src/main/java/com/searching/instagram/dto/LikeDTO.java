package com.searching.instagram.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.searching.instagram.entity.enums.LikeType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class LikeDTO {
    private Long id;

    @Positive
    private Long profileId;
    @Positive
    private Long postId;
    @NotEmpty(message = "likeType is Null or is Empty")
    private LikeType likeType;

    @PastOrPresent
    private LocalDateTime created_date;

}

package com.searching.instagram.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class LookDTO {
    private Long id;

    @Positive
    private Integer profileId;

    @Positive
    private Integer postId;

    @PastOrPresent
    private LocalDateTime created_date;


}

package com.searching.instagram.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.searching.instagram.entity.PostEntity;
import com.searching.instagram.entity.ProfileEntity;
import com.searching.instagram.entity.enums.SaveType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class SaveDTO {
    private Long id;

    @Positive
    private Long profileId;

    @Positive
    private Long postId;

    @PastOrPresent
    private LocalDateTime created_date;
}

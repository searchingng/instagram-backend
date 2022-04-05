package com.searching.instagram.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.searching.instagram.entity.ProfileEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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
public class PostDTO {
    private Long id;

    @NotEmpty(message = "title is Null or is Empty")
    private String title;
    @NotEmpty(message = "location is Null or is Empty")
    private String location;
    private String URI;
    @Positive
    private Long profileId;

    @PastOrPresent
    private LocalDateTime created_date;
}

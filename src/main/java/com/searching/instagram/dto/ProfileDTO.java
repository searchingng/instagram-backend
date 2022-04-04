package com.searching.instagram.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.searching.instagram.entity.enums.ProfileGender;
import com.searching.instagram.entity.enums.ProfileStatus;
import com.searching.instagram.entity.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ProfileDTO {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String fullName;
    private String username;
    private String password;
    private String website;
    private String bio;
    private String email;
    private String phone;
    private LocalDate birthDate;

    private ProfileGender gender;
    private Role role;
    private ProfileStatus status = ProfileStatus.ACTIVE;


    private String jwt;
}

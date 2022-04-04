package com.searching.instagram.entity;

import com.searching.instagram.entity.base.BaseEntity;
import com.searching.instagram.entity.enums.ProfileGender;
import com.searching.instagram.entity.enums.ProfileStatus;
import com.searching.instagram.entity.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "profile")
public class ProfileEntity extends BaseEntity {

    private String fullName;
    private String username;
    private String password;
    private String website;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private ProfileGender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private ProfileStatus status = ProfileStatus.ACTIVE;

    private LocalDate birthDate;

}

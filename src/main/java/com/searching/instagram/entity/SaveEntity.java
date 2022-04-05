package com.searching.instagram.entity;

import com.searching.instagram.entity.base.BaseEntity;
import com.searching.instagram.entity.enums.SaveType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "save")
public class SaveEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile")
    private ProfileEntity profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post")
    private PostEntity post;

    @Enumerated(EnumType.STRING)
    @Column(name = "save_type")
    private SaveType saveType;


}

package com.searching.instagram.entity;

import com.searching.instagram.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "coment")
public class CommentEntity extends BaseEntity {

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ProfileEntity profile;


}

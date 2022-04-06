package com.searching.instagram.entity;

import com.searching.instagram.entity.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "post")
public class PostEntity extends BaseEntity {

    @Column(nullable = false)
    private String content;

    private String location;

    private String hashtag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "profile_id")
    private Long profileId;

    @OneToMany
    @JoinTable(
            name = "post_attach",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "attach_id")}
    )
    private Set<AttachEntity> attachs;


}

package com.searching.instagram.entity;


import com.searching.instagram.entity.base.BaseEntity;
import com.searching.instagram.entity.base.BaseUUIDEntity;
import com.searching.instagram.entity.enums.LikeType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "likes")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {
        "profile_id", "post_id" }))

public class LikeEntity extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "profile_id")
    private Long profileId;
}

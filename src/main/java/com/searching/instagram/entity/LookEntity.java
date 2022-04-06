package com.searching.instagram.entity;

import com.searching.instagram.entity.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "look")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {
        "profile_id", "post_id" }))
public class LookEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "profile_id")
    private Long profileId;

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private PostEntity post;

    @Column(name = "post_id")
    private Long postId;
}

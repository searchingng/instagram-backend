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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {
        "profile_id", "post_id" }))
public class SaveEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "profile_id")
    private Long profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private PostEntity post;

    @Column(name = "post_id")
    private Long postId;


}

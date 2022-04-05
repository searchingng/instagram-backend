package com.searching.instagram.entity;

import com.searching.instagram.dto.ProfileDTO;
import com.searching.instagram.entity.base.BaseEntity;
import com.searching.instagram.entity.base.BaseUUIDEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "following")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {
        "profile_id", "follower_id" }))
@Setter
@Getter
@NoArgsConstructor
public class FollowingEntity extends BaseUUIDEntity {

    @ManyToOne
    @JoinColumn(name = "follower_id", insertable = false, updatable = false)
    private ProfileEntity follower;

    @ManyToOne
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "follower_id")
    private Long followerId;

}

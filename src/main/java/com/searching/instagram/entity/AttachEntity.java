package com.searching.instagram.entity;

import com.searching.instagram.entity.base.BaseUUIDEntity;
import com.searching.instagram.entity.enums.AttachType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "attach")
@Setter
@Getter
@NoArgsConstructor
public class AttachEntity extends BaseUUIDEntity {

    private String name;
    private String extension;
    private String contentType;
    private String path;
    private Long size;
    private String url;

    @Enumerated(EnumType.STRING)
    private AttachType type;

    @ManyToOne
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "profile_id")
    private Long profileId;


}

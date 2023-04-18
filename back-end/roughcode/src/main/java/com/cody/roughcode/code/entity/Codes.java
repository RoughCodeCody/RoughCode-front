package com.cody.roughcode.code.entity;

import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "codes")
public class Codes extends BaseTimeEntity {
    @EmbeddedId
    private CodeId codesId;

    @Column(name = "title", length = 20, nullable = false)
    private String title;

    @Builder.Default
    @Column(name = "likes", nullable = true)
    private int likes = 0;

    @Builder.Default
    @Column(name = "favorites", nullable = true)
    private int favorites = 0;

    @Builder.Default
    @Column(name = "review_cnt", nullable = true)
    private int reviewCnt = 0;

    @Builder.Default
    @Column(name = "content", length = 255, nullable = true)
    private String content = "";

    @ManyToOne
    @JoinColumn(name="users_id")
    private Users users;

    @OneToMany(mappedBy = "codes")
    private List<Reviews> reviews;

    @OneToMany(mappedBy = "codes")
    private List<CodeSelectedTags> tags;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "projects_id", referencedColumnName = "projects_id", insertable = false, updatable = false),
            @JoinColumn(name = "projects_version", referencedColumnName = "version", insertable = false, updatable = false)
    })
    private Projects projects;
}

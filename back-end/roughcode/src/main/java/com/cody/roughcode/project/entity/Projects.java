package com.cody.roughcode.project.entity;

import com.cody.roughcode.code.entity.Codes;
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
@Table(name = "projects")
public class Projects extends BaseTimeEntity {
    @EmbeddedId
    private ProjectId projectsId;

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
    @Column(name = "url", length = 255, nullable = true)
    private String url = "";

    @Column(name = "img", length = 255, nullable = false)
    private String img;

    @Builder.Default
    @Column(name = "content", length = 255, nullable = true)
    private String content = "";

    @Builder.Default
    @Column(name = "complaint", nullable = true)
    private int complaint = 0;

    @Builder.Default
    @Column(name = "closed", nullable = true)
    private boolean closed = false;

    @Column(name = "notice", length = 255, nullable = false)
    private String notice;

    @ManyToOne
    @JoinColumn(name="users_id")
    private Users users;

    @OneToOne(mappedBy = "projects")
    private Codes codes;

    @OneToMany(mappedBy = "projects")
    private List<Feedbacks> feedbacks;

    @OneToMany(mappedBy = "projects")
    private List<ProjectSelectedTags> tags;
}

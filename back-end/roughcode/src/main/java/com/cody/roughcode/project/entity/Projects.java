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

    @Column(name = "likes", nullable = true)
    private int likes = 0;

    @Column(name = "favorites", nullable = true)
    private int favorites = 0;

    @Column(name = "review_cnt", nullable = true)
    private int reviewCnt = 0;

    @Column(name = "url", length = 255, nullable = true)
    private String url = "";

    @Column(name = "img", length = 255, nullable = false)
    private String img;

    @Column(name = "content", length = 255, nullable = true)
    private String content = "";

    @Column(name = "complaint", nullable = true)
    private int complaint = 0;

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

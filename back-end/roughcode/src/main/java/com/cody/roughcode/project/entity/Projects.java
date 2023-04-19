package com.cody.roughcode.project.entity;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.List;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
public class Projects extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projects_id", nullable = false, columnDefinition = "BIGINT ")
    private Long projectsId;

    @Column(name = "num", nullable = false)
    private Long num;

    @Column(name = "version", nullable = false)
    private int version;

    @Column(name = "title", length = 20, nullable = false)
    private String title;

    @Builder.Default
    @Column(name = "like_cnt", nullable = true)
    private int likeCnt = 0;

    @Builder.Default
    @Column(name = "review_cnt", nullable = true)
    private int reviewCnt = 0;

    @Builder.Default
    @Column(name = "favorite_cnt", nullable = true)
    private int favoriteCnt = 0;

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

    @Column(name = "notice", nullable = false, columnDefinition = "text")
    private String notice;

    @Column(name = "introduction", nullable = false)
    private String introduction;

    @OneToMany(mappedBy = "projects")
    private List<Codes> projectsCodes;

    @OneToMany(mappedBy = "projects")
    private List<Feedbacks> projectsFeedbacks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_writer_id", nullable = false)
    private Users projectWriter;

    @OneToMany(mappedBy = "projects")
    private List<ProjectSelectedTags> selectedTags;
}

package com.cody.roughcode.project.entity;

import com.cody.roughcode.code.entity.Codes;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects_info")
public class ProjectsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Column(columnDefinition = "text")
    private String content;

    @Builder.Default
    @Column(name = "url", length = 255, nullable = true)
    private String url = "";

    @Builder.Default
    @Column(name = "complaint", nullable = true)
    private int complaint = 0;


    @Column(name = "notice", nullable = false, columnDefinition = "text")
    private String notice;

    @Builder.Default
    @Column(name = "favorite_cnt", nullable = true)
    private int favoriteCnt = 0;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projects_id", nullable = false)
    private Projects projects;

    @OneToMany(mappedBy = "projects")
    private List<Feedbacks> projectsFeedbacks;
}

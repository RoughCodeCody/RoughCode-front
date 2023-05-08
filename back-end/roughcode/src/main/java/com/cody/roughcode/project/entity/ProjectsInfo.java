package com.cody.roughcode.project.entity;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.project.dto.req.ProjectReq;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long id;

    @Column(columnDefinition = "text")
    private String content;

    @Column(name = "url", length = 255, nullable = false)
    private String url;

    @Builder.Default
    @Column(name = "complaint", nullable = true)
    private int complaint = 0;

    @Column(name = "notice", nullable = false, columnDefinition = "text")
    private String notice;

    @Builder.Default
    @Column(name = "favorite_cnt", nullable = true)
    private int favoriteCnt = 0;

    @Builder.Default
    @Column(name = "closed_checked", nullable = true)
    private LocalDateTime closedChecked = null;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projects_id", nullable = false)
    private Projects projects;

    @OneToMany(mappedBy = "projectsInfo")
    private List<Feedbacks> feedbacks;

    public void setProjects(Projects projects) {
        this.projects = projects;
    }

    public void updateProject(ProjectReq req) {
        this.content = req.getContent();
        this.url = req.getUrl();
        this.notice = req.getNotice();
    }

    public void setFeedbacks(Feedbacks feedbacks) {
        if(this.feedbacks == null) this.feedbacks = new ArrayList<>();
        this.feedbacks.add(feedbacks);
    }

    public void setClosedChecked(LocalDateTime now) {
        this.closedChecked = now;
    }

    public void favoriteCntDown() {
        this.favoriteCnt -= 1;
    }

    public void favoriteCntUp() {
        this.favoriteCnt += 1;
    }
}

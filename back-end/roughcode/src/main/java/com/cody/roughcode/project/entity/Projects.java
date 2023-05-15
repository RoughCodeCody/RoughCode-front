package com.cody.roughcode.project.entity;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.project.dto.req.ProjectReq;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.util.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
public class Projects extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projects_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
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
    @Column(name = "feedback_cnt", nullable = true)
    private int feedbackCnt = 0;

    @Column(name = "img", length = 255, nullable = false)
    private String img;

    @Builder.Default
    @Column(name = "closed", nullable = true)
    private boolean closed = false;

    @Column(name = "introduction", nullable = false)
    private String introduction;

    @Builder.Default
    @Column(name = "expire_date", nullable = true)
    private LocalDateTime expireDate = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_writer_id", nullable = false)
    private Users projectWriter;

    @JsonIgnore
@OneToMany(mappedBy = "projects")
    private List<ProjectSelectedTags> selectedTags;

    @JsonIgnore
@OneToMany(mappedBy = "projects")
    private List<SelectedFeedbacks> selectedFeedbacks;

    @JsonIgnore
@OneToMany(mappedBy = "projects")
    private List<Codes> projectsCodes;

    @JsonIgnore
@OneToMany(mappedBy = "projects")
    private List<ProjectFavorites> projectFavorites;

    @JsonIgnore
@OneToMany(mappedBy = "projects")
    private List<ProjectLikes> projectLikes;

    public void updateProject(ProjectReq req) {
        this.title = req.getTitle();
        this.introduction = req.getIntroduction();
    }

    public void setImgUrl(String imgUrl) {
        this.img = imgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projects projects = (Projects) o;
        return version == projects.version && projectsId.equals(projects.projectsId) && num.equals(projects.num) && projectWriter.equals(projects.projectWriter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectsId, num, version, projectWriter);
    }

    public void setCodes(List<Codes> codesList) {
        this.projectsCodes = codesList;
    }

    public void close(boolean b) {
        this.closed = b;
    }

    public void feedbackCntDown(){
        this.feedbackCnt -= 1;
    }

    public void feedbackCntUp() {
        this.feedbackCnt += 1;
    }

    public void likeCntUp() {
        this.likeCnt += 1;
    }

    public void likeCntDown() {
        this.likeCnt -= 1;
    }

    public void setStatus(boolean status) {
        this.closed = status;
    }

    public void setExpireDate() {
        this.expireDate = LocalDateTime.now().plusDays(30L);
    }
}

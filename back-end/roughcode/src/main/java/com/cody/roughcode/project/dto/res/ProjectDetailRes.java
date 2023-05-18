package com.cody.roughcode.project.dto.res;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.entity.ProjectsInfo;
import com.cody.roughcode.user.entity.Users;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectDetailRes {
    private Long projectId;
    private String userName;
    private Boolean mine;
    private String title;
    private int version;
    private LocalDateTime date;
    private int likeCnt;
    private int feedbackCnt;
    private int favoriteCnt;
    private String img;
    private Boolean closed;
    private String url;
    private List<CodeInfoRes> code;
    private List<ProjectTagsRes> tags;
    private Boolean liked;
    private Boolean favorite;
    private List<VersionRes> versions;
    private List<FeedbackRes> feedbacks;
    private String content;
    private String notice;
    private String introduction;

    public ProjectDetailRes(Projects project, ProjectsInfo projectsInfo, List<ProjectTagsRes> tagList,
                            Boolean liked, Boolean favorite, Users user) {
        this.projectId = project.getProjectsId();
        this.userName = project.getProjectWriter().getName();
        this.mine = (project.getProjectWriter().equals(user));
        this.title = project.getTitle();
        this.version = project.getVersion();
        this.date = project.getCreatedDate();
        this.likeCnt = project.getLikeCnt();
        this.feedbackCnt = project.getFeedbackCnt();
        this.favoriteCnt = projectsInfo.getFavoriteCnt();
        this.img = project.getImg();
        this.closed = project.isClosed();
        this.content = projectsInfo.getContent();
        this.url = projectsInfo.getUrl();
        List<CodeInfoRes> codeInfoResList = new ArrayList<>();
        if(project.getProjectsCodes() != null)
            for (Codes code : project.getProjectsCodes()) {
                CodeInfoRes codeInfoRes = new CodeInfoRes(code);
                codeInfoResList.add(codeInfoRes);
            }
        this.code = codeInfoResList;
        this.tags = tagList;
        this.liked = liked;
        this.favorite = favorite;
        this.notice = projectsInfo.getNotice();
        this.introduction = project.getIntroduction();
    }

    public void setVersions(List<VersionRes> versionResList) {
        this.versions = versionResList;
    }

    public void setFeedbacks(List<FeedbackRes> feedbackResList) {
        this.feedbacks = feedbackResList;
    }
}

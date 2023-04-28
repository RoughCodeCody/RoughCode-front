package com.cody.roughcode.project.dto.res;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.project.entity.Feedbacks;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.entity.ProjectsInfo;
import lombok.*;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.apache.bcel.classfile.Code;

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
    private String title;
    private int version;
    private LocalDateTime date;
    private int likeCnt;
    private int feedbackCnt;
    private int favoriteCnt;
    private String img;
    private Boolean closed;
    private String url;
    private List<CodeInfoRes> codeId;
    private List<String> tags;
    private Boolean liked;
    private Boolean favorite;
    private List<VersionRes> versions;
    private List<FeedbackRes> feedbacks;
    private String content;
    private String notice;

    public ProjectDetailRes(Projects project, ProjectsInfo projectsInfo, List<String> tagList,
                            Boolean liked, Boolean favorite) {
        this.projectId = project.getProjectsId();
        this.title = project.getTitle();
        this.version = project.getVersion();
        this.date = project.getModifiedDate();
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
        this.codeId = codeInfoResList;
        this.tags = tagList;
        this.liked = liked;
        this.favorite = favorite;
        this.notice = projectsInfo.getNotice();
    }

    public void setVersions(List<VersionRes> versionResList) {
        this.versions = versionResList;
    }

    public void setFeedbacks(List<FeedbackRes> feedbackResList) {
        this.feedbacks = feedbackResList;
    }
}

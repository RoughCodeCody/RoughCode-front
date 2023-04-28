package com.cody.roughcode.project.dto.res;

import com.cody.roughcode.project.entity.Feedbacks;
import com.cody.roughcode.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRes { // 1.반영된 피드백, 2.내가 쓴 피드백, 3.나머지.. 순으로 정렬
    private Long feedbackId;
    private Long userId;
    private String userName;
    private String content;
    private int like;
    private int selected;
    private LocalDateTime date;
    private Boolean liked;

    public FeedbackRes(Feedbacks f, Boolean feedbackLiked) {
        this.feedbackId = f.getFeedbacksId();
        if(f.getUsers() != null) {
            this.userId = f.getUsers().getUsersId();
            this.userName = f.getUsers().getName();
        } else {
            this.userId = 0L;
            this.userName = "";
        }
        this.content = f.getContent();
        this.like = f.getLikeCnt();
        this.selected = f.getSelected();
        this.date = f.getModifiedDate();
        this.liked = feedbackLiked;
    }
}
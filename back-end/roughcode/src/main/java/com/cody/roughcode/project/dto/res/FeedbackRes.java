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
    private Boolean selected;
    private LocalDateTime date;

    public FeedbackRes(Feedbacks f, Boolean isSelected) {
        this.feedbackId = f.getFeedbacksId();
        this.userId = f.getUsers().getUsersId();
        this.userName = f.getUsers().getName();
        this.content = f.getContent();
        this.like = f.getLikeCnt();
        this.selected = isSelected;
        this.date = f.getModifiedDate();
    }
}
package com.cody.roughcode.project.dto.res;

import com.cody.roughcode.project.entity.Feedbacks;
import com.cody.roughcode.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FeedbackInfoRes {
    private Long feedbackId;
    private Long userId;
    private String userName;
    private String content;
    private Boolean selected;

    public FeedbackInfoRes(Feedbacks f, Users users) {
        this.feedbackId = f.getFeedbacksId();
        this.userId = users.getUsersId();
        this.userName = users.getName();
        this.content = f.getContent();
        this.selected = f.getSelected() > 0;
    }
}

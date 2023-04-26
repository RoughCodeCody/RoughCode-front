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
    private int version;

    public FeedbackInfoRes(Feedbacks f, int version, Users users) {
        this.feedbackId = f.getFeedbacksId();
        if(users != null) {
            this.userId = users.getUsersId();
            this.userName = users.getName();
        } else {
            this.userId = 0L;
            this.userName = "";
        }
        this.content = f.getContent();
        this.selected = f.getSelected() > 0;
        this.version = version;
    }
}

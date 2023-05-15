package com.cody.roughcode.code.dto.res;

import com.cody.roughcode.code.entity.Reviews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReviewSearchRes {

    private Long reviewId;
    private Long userId;
    private String userName;
    private String content;
    private Boolean selected;
    private int likeCnt;
    private Boolean liked;
    private LocalDateTime date;

    public ReviewSearchRes(Reviews review, Boolean liked) {
        this.reviewId = review.getReviewsId();
        if(review.getUsers() != null) {
            this.userId = review.getUsers().getUsersId();
            this.userName = review.getUsers().getName();
        } else {
            this.userId = 0L;
            this.userName = "";
        }
        this.content = review.getContent();
        this.selected = review.getSelected() > 0;
        this.likeCnt = review.getLikeCnt();
        this.liked = liked;
        this.date = review.getCreatedDate();
    }

}

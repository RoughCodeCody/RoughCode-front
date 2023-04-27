package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.res.ReReviewRes;
import com.cody.roughcode.code.dto.res.ReviewRes;
import com.cody.roughcode.code.entity.Reviews;

public interface ReviewsService {

    default ReviewRes toDto(Reviews reviews, Boolean liked) {
        return ReviewRes.builder()
                .reviewId(reviews.getReviewsId())
                .userId(reviews.getUsers().getUsersId())
                .userName(reviews.getUsers().getName())
                .liked(liked)
                .content(reviews.getContent())
                .date(reviews.getModifiedDate())
                .build();
    }
}

package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.res.ReReviewRes;
import com.cody.roughcode.code.entity.ReReviews;
import com.cody.roughcode.user.dto.res.UserResp;
import com.cody.roughcode.user.entity.Users;

public interface ReReviewsService {

    default ReReviewRes toDto(ReReviews reReviews, Boolean liked) {
        return ReReviewRes.builder()
                .reReviewId(reReviews.getReReviewsId())
                .userId(reReviews.getUsers().getUsersId())
                .userName(reReviews.getUsers().getName())
                .liked(liked)
                .content(reReviews.getContent())
                .createdDate(reReviews.getCreatedDate())
                .modifiedDate(reReviews.getModifiedDate())
                .build();
    }
}

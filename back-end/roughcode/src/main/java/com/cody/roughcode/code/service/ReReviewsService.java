package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.ReReviewReq;
import com.cody.roughcode.code.dto.res.ReReviewRes;

import java.util.List;

public interface ReReviewsService {
    int insertReReview(ReReviewReq req, Long usersId);
    int updateReReview(ReReviewReq req, Long usersId);
    List<ReReviewRes> getReReviewList(Long reviewsId, Long usersId);
    int deleteReReview(Long reReviewsId, Long usersId);
    int likeReReview(Long reReviewsId, Long usersId);
    int reReviewComplain(Long reReviewsId, Long usersId);
}

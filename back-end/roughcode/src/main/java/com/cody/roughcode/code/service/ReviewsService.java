package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.ReviewReq;
import com.cody.roughcode.code.dto.res.ReviewDetailRes;
import com.cody.roughcode.code.dto.res.ReviewRes;

public interface ReviewsService {

    Long insertReview(ReviewReq req, Long userId);

    ReviewDetailRes getReview(Long reviewId, Long userId);

    int updateReview(ReviewReq reviewReq, Long reviewId, Long userId);

    int deleteReview(Long reviewId, Long userId);

    int likeReview(Long reviewId, Long userId);

    int complainReview(Long reviewId, Long userId);

}

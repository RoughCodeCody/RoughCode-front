package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.ReviewReq;

public interface ReviewsService {

    Long insertReview(ReviewReq req, Long userId);

    int updateReview(ReviewReq reviewReq, Long reviewId, Long userId);

    int deleteReview(Long reviewId, Long userId);
}

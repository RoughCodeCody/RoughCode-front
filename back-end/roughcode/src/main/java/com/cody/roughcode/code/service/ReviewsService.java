package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.ReviewReq;

public interface ReviewsService {

    Long insertReview(ReviewReq req, Long userId);

}

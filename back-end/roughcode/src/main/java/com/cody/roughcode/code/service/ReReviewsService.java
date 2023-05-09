package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.ReReviewReq;

import javax.mail.MessagingException;

public interface ReReviewsService {
    int insertReReview(ReReviewReq req, Long usersId);
}

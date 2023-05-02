package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.ReviewReq;
import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.entity.CodesInfo;
import com.cody.roughcode.code.entity.Reviews;
import com.cody.roughcode.code.repository.CodesInfoRepository;
import com.cody.roughcode.code.repository.CodesRepository;
import com.cody.roughcode.code.repository.ReviewsRepository;
import com.cody.roughcode.exception.SaveFailedException;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewsServiceImpl implements ReviewsService{

    private final UsersRepository usersRepository;
    private final CodesRepository codesRepository;
    private final CodesInfoRepository codesInfoRepository;
    private final ReviewsRepository reviewsRepository;

    @Override
    @Transactional
    public Long insertReview(ReviewReq req, Long userId) {

        Users user = usersRepository.findByUsersId(userId);
        Codes code = codesRepository.findByCodesId(req.getCodeId());

        if(code == null) {
            throw new NullPointerException("일치하는 코드가 없습니다");
        }

        String selectedRange = req.getSelectedRange().toString();

        Long reviewId = -1L;
        // 코드 리뷰 저장
        try {
            Reviews reviews = Reviews.builder()
                    .lineNumbers(selectedRange)
                    .codeContent(req.getCodeContent())
                    .content(req.getContent())
                    .users(user)
                    .codes(code)
                    .build();
            Reviews savedReviews = reviewsRepository.save(reviews);
            reviewId = savedReviews.getReviewsId();

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SaveFailedException(e.getMessage());
        }

        return reviewId;
    }

}

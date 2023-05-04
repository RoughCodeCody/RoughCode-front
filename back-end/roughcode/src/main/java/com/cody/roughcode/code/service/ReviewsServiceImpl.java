package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.ReviewReq;
import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.entity.Reviews;
import com.cody.roughcode.code.repository.CodesInfoRepository;
import com.cody.roughcode.code.repository.CodesRepository;
import com.cody.roughcode.code.repository.ReviewsRepository;
import com.cody.roughcode.exception.*;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

            // 리뷰를 등록하는 코드의 reviewCnt 값 +1
            code.reviewCntUp();

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SaveFailedException(e.getMessage());
        }

        return reviewId;
    }

    @Override
    @Transactional
    public int updateReview(ReviewReq req, Long reviewId, Long userId) {

        Users user = usersRepository.findByUsersId(userId);

        // 코드 리뷰 작성자 확인
        if (user == null) {
            throw new NullPointerException("일치하는 유저가 존재하지 않습니다");
        }

        // 기존 코드 리뷰 가져오기
        Reviews target = reviewsRepository.findByReviewsId(reviewId);
        if (target == null) {
            throw new NullPointerException("일치하는 코드 리뷰가 존재하지 않습니다");
        }

        if (!target.getUsers().equals(user)) {
            // 코드 리뷰 작성자와 사용자가 일치하지 않는 경우
            throw new NotMatchException();
        }

        try {
            // 코드 리뷰 정보 업데이트
            if(StringUtils.hasText(req.getContent())){
                log.info("코드 리뷰 정보 수정(상세설명): "+ req.getContent());
                target.updateContent(req.getContent());
            }
            if(StringUtils.hasText(req.getCodeContent())){
                log.info("코드 리뷰 정보 수정(코드내용): "+ req.getCodeContent());
                target.updateCodeContent(req.getCodeContent());
            }
            if(req.getSelectedRange().size()>0){
                log.info("코드 리뷰 정보 수정(선택구간): "+ req.getSelectedRange());
                target.updateLineNumbers(req.getSelectedRange());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UpdateFailedException(e.getMessage());
        }

        return 1;
    }

}

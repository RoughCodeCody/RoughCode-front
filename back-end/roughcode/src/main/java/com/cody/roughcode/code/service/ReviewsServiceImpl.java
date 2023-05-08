package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.ReviewReq;
import com.cody.roughcode.code.dto.res.ReReviewRes;
import com.cody.roughcode.code.dto.res.ReviewRes;
import com.cody.roughcode.code.entity.*;
import com.cody.roughcode.code.repository.*;
import com.cody.roughcode.exception.*;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewsServiceImpl implements ReviewsService{

    private final UsersRepository usersRepository;
    private final CodesRepository codesRepository;
    private final CodesInfoRepository codesInfoRepository;
    private final ReviewsRepository reviewsRepository;
    private final ReviewLikesRepository reviewLikesRepository;
    private final ReReviewsRepository reReviewsRepository;
    private final ReReviewLikesRepository reReviewLikesRepository;

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
    public ReviewRes getReview(Long reviewId, Long userId) {

        Users user = usersRepository.findByUsersId(userId);
        Reviews review = reviewsRepository.findByReviewsId(reviewId);

        if (review == null) {
            throw new NullPointerException("일치하는 코드 리뷰가 존재하지 않습니다");
        }

        // 내가 좋아요 눌렀는지 여부
        ReviewLikes reviewLike = (user != null) ? reviewLikesRepository.findByReviewsAndUsers(review, user) : null;
        Boolean reviewLiked = reviewLike != null;

        // 리리뷰 목록
        List<ReReviewRes> reReviewResList = new ArrayList<>();
        for (ReReviews reReview : review.getReReviews()) {
            ReReviewLikes reReviewLike = (user != null) ? reReviewLikesRepository.findByReReviewsAndUsers(reReview, user) : null;
            Boolean reReviewLiked = reReviewLike != null;
            reReviewResList.add(ReReviewRes.toDto(reReview, reReviewLiked));
        }

        return ReviewRes.toDto(review, reviewLiked, reReviewResList);
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

        // 코드 리뷰 작성자와 사용자가 일치하지 않는 경우
        if (target.getUsers() == null || !target.getUsers().equals(user)) {
            throw new NotMatchException();
        }

        // 채택된 코드 리뷰인 경우
        if (target.getSelected() > 0) {
            throw new SelectedException("채택된 코드 리뷰는 수정할 수 없습니다");
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

    @Override
    @Transactional
    public int deleteReview(Long reviewId, Long userId) {

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

        // 코드 리뷰 작성자와 사용자가 일치하지 않는 경우
        if (target.getUsers() == null || !target.getUsers().equals(user)) {
            throw new NotMatchException();
        }

        // 채택된 코드 리뷰인 경우
        if (target.getSelected() > 0) {
            throw new SelectedException("채택된 코드 리뷰는 삭제할 수 없습니다");
        }

        try {
            // 코드의 리뷰 수 감소
            Codes codes = target.getCodes();
            codes.reviewCntDown();

            // 코드 리리뷰 좋아요 목록 삭제
            reReviewLikesRepository.deleteAllByReviewId(reviewId);

            // 코드 리리뷰 삭제
            reReviewsRepository.deleteAllByReviews(target);

            // 코드 리뷰 좋아요 목록 삭제
            reviewLikesRepository.deleteAllByReviewId(reviewId);

            // 코드 리뷰 삭제
            reviewsRepository.delete(target);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UpdateFailedException(e.getMessage());
        }

        return 1;
    }

}

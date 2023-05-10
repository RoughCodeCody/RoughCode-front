package com.cody.roughcode.code.service;

import com.cody.roughcode.alarm.dto.req.AlarmReq;
import com.cody.roughcode.alarm.service.AlarmServiceImpl;
import com.cody.roughcode.code.dto.req.ReviewReq;
import com.cody.roughcode.code.dto.res.ReReviewRes;
import com.cody.roughcode.code.dto.res.ReviewCodeRes;
import com.cody.roughcode.code.dto.res.ReviewDetailRes;
import com.cody.roughcode.code.entity.*;
import com.cody.roughcode.code.repository.*;
import com.cody.roughcode.email.service.EmailServiceImpl;
import com.cody.roughcode.exception.NotMatchException;
import com.cody.roughcode.exception.SaveFailedException;
import com.cody.roughcode.exception.SelectedException;
import com.cody.roughcode.exception.UpdateFailedException;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewsServiceImpl implements ReviewsService {

    private final UsersRepository usersRepository;
    private final CodesRepository codesRepository;
    private final CodesInfoRepository codesInfoRepository;
    private final CodeLikesRepository codeLikesRepository;
    private final CodeFavoritesRepository codeFavoritesRepository;
    private final ReviewsRepository reviewsRepository;
    private final ReviewLikesRepository reviewLikesRepository;
    private final ReReviewsRepository reReviewsRepository;
    private final ReReviewLikesRepository reReviewLikesRepository;

    private final AlarmServiceImpl alarmService;
    private final EmailServiceImpl emailService;

    @Override
    @Transactional
    public Long insertReview(ReviewReq req, Long userId) throws MessagingException {

        Users user = usersRepository.findByUsersId(userId);
        Codes code = codesRepository.findByCodesId(req.getCodeId());

        if (code == null) {
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

        // 알람 전송
        AlarmReq alarmContent = AlarmReq.builder()
                .content(List.of("작성한", code.getTitle() + " ver" + code.getVersion(), "새 리뷰 등록"))
                .userId(code.getCodeWriter().getUsersId())
                .postId(code.getCodesId())
                .section("code")
                .build();

        // 작성한 무슨무슨 코드 ver1에 새 리뷰 등록 -> [“작성한”, “무슨무슨 코드 ver1”, “새 리뷰 등록”]
        alarmService.insertAlarm(alarmContent);

        emailService.sendAlarm("새 리뷰가 등록되었습니다", alarmContent);

        return reviewId;
    }

    @Override
    public ReviewDetailRes getReview(Long reviewId, Long userId) {

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

        // 코드에 등록된 태그 목록
        Codes code = review.getCodes();
        CodesInfo codesInfo = codesInfoRepository.findByCodes(code);
        List<String> tagList = getTagNames(code);

        // 내가 즐겨찾기/좋아요 눌렀는지 여부
        CodeFavorites myFavorite = (user != null) ? codeFavoritesRepository.findByCodesAndUsers(code, user) : null;
        CodeLikes myLike = (user != null) ? codeLikesRepository.findByCodesAndUsers(code, user) : null;
        Boolean favorite = myFavorite != null;
        Boolean liked = myLike != null;

        // 연결된 프로젝트 정보(id, 제목) 저장
        Long connectedProjectId = null;
        String connectedProjectTitle = null;
        if (code.getProjects() != null) {
            connectedProjectId = code.getProjects().getProjectsId();
            connectedProjectTitle = code.getProjects().getTitle();
        }

        ReviewCodeRes reviewCodeRes = ReviewCodeRes.builder()
                .codeId(code.getCodesId())
                .version(code.getVersion())
                .title(code.getTitle())
                .likeCnt(code.getLikeCnt())
                .liked(liked)
                .favoriteCnt(codesInfo.getFavoriteCnt())
                .favorite(favorite)
                .tags(tagList)
                .userName(code.getCodeWriter().getName())
                .projectTitle(connectedProjectTitle)
                .projectId(connectedProjectId)
                .build();

        return ReviewDetailRes.toDto(codesInfo.getGithubUrl(), review, reviewLiked, reReviewResList, reviewCodeRes);
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
            target.updateContent(req.getContent());
            target.updateCodeContent(req.getCodeContent());
            target.updateLineNumbers(req.getSelectedRange());
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

    @Override
    @Transactional
    public int likeReview(Long reviewId, Long userId) {

        Users user = usersRepository.findByUsersId(userId);

        // 좋아요 누른 사용자 확인
        if (user == null) {
            throw new NullPointerException("일치하는 유저가 존재하지 않습니다");
        }

        // 기존 코드 리뷰 가져오기
        Reviews target = reviewsRepository.findByReviewsId(reviewId);
        if (target == null) {
            throw new NullPointerException("일치하는 코드 리뷰가 존재하지 않습니다");
        }

        // 좋아요를 누른 여부 확인 (눌려있다면 취소 처리, 새로 누른 경우 등록 처리)
        ReviewLikes reviewLikes = reviewLikesRepository.findByReviewsAndUsers(target, user);

        // 좋아요가 눌려있다면 취소 처리
        if (reviewLikes != null) {
            // ReviewLikes 데이터 삭제
            reviewLikesRepository.delete(reviewLikes);
            // 코드 리뷰 좋아요 수 감소
            target.likeCntDown();
        } else { // 새로 누른 경우 등록 처리
            // ReviewLikes 데이터 추가
            reviewLikesRepository.save(
                    ReviewLikes.builder()
                            .reviews(target)
                            .users(user)
                            .build());

            // 코드 리뷰 좋아요 수 증가
            target.likeCntUp();
        }

        // 좋아요 수 반환
        return target.getLikeCnt();
    }

    @Override
    @Transactional
    public int complainReview(Long reviewId, Long userId) {

        Users user = usersRepository.findByUsersId(userId);
        if (user == null) {
            throw new NullPointerException("일치하는 유저가 존재하지 않습니다");
        }
        // 기존 코드 리뷰 가져오기
        Reviews target = reviewsRepository.findByReviewsId(reviewId);
        if (target == null) {
            throw new NullPointerException("일치하는 코드 리뷰가 존재하지 않습니다");
        }
        List<String> complainList = (target.getComplaint().equals("")) ? new ArrayList<>() : new ArrayList<>(List.of(target.getComplaint().split(",")));

        if (target.getCodeContent() == null || target.getCodeContent() == "") {
            throw new SelectedException("이미 삭제된 코드 리뷰입니다");
        }
        if (target.getComplaint().contains(String.valueOf(userId))) {
            throw new SelectedException("이미 신고한 코드 리뷰입니다");
        }
        log.info(complainList.size() + "번 신고된 코드 리뷰입니다");
        if(complainList.size() >= 10){
            target.deleteCodeContent();
        }
        complainList.add(String.valueOf(userId));
        target.setComplaint(complainList);

        return 1;
    }

    private static List<String> getTagNames(Codes code) {
        List<String> tagList = new ArrayList<>();
        if (code.getSelectedTags() != null)
            for (CodeSelectedTags selected : code.getSelectedTags()) {
                tagList.add(selected.getTags().getName());
            }
        return tagList;
    }
}

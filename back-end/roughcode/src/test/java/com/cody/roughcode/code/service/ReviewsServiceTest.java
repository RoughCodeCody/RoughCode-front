package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.ReviewReq;
import com.cody.roughcode.code.dto.res.ReviewDetailRes;
import com.cody.roughcode.code.entity.*;
import com.cody.roughcode.code.repository.*;
import com.cody.roughcode.exception.NotMatchException;
import com.cody.roughcode.exception.SelectedException;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class) // 가짜 객체 주입을 사용
public class ReviewsServiceTest {

    @InjectMocks
    private ReviewsServiceImpl reviewsService;

    @Mock
    private ReviewsRepository reviewsRepository;
    @Mock
    private ReviewLikesRepository reviewLikesRepository;
    @Mock
    private ReReviewsRepository reReviewsRepository;
    @Mock
    private ReReviewLikesRepository reReviewLikesRepository;

    @Mock
    private CodesRepository codesRepository;
    @Mock
    private UsersRepository usersRepository;

    final Users user = Users.builder()
            .usersId(1L)
            .email("cody306@gmail.com")
            .name("코디")
            .roles(List.of(String.valueOf(ROLE_USER)))
            .build();

    final Users user2 = Users.builder()
            .usersId(2L)
            .email("cody306@gmail.com")
            .name("코디")
            .roles(List.of(String.valueOf(ROLE_USER)))
            .build();


    final Codes code = Codes.builder()
            .codesId(1L)
            .num(1L)
            .version(1)
            .title("개발새발 코드")
            .codeWriter(user)
            .likeCnt(3)
            .reviewCnt(2)
            .build();

    final Reviews review = Reviews.builder()
            .reviewsId(1L)
            .lineNumbers("[[1,2],[3,4]]")
            .codeContent("import sys ..")
            .content("설명설명")
            .codes(code)
            .users(user)
            .likeCnt(3)
            .complaint("2")
            .build();

    final ReviewReq req = ReviewReq.builder()
            .codeId(1L)
            .selectedRange(List.of(List.of(1, 2), List.of(4, 6)))
            .codeContent("hello world")
            .content("설명설명")
            .build();

    @DisplayName("코드 리뷰 등록 성공")
    @Test
    void insertReviewSucceed() {
        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(code).when(codesRepository).findByCodesId(any(Long.class));
        doReturn(review).when(reviewsRepository).save(any(Reviews.class));

        // when
        Long savedReviewId = reviewsService.insertReview(req, 1L);

        // then
        assertThat(savedReviewId).isEqualTo(1L);
    }

    @DisplayName("코드 리뷰 등록 성공 - 익명 사용자")
    @Test
    void insertReviewSucceedUserNull() {
        // given
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(code).when(codesRepository).findByCodesId(any(Long.class));
        doReturn(review).when(reviewsRepository).save(any(Reviews.class));

        // when
        Long savedReviewId = reviewsService.insertReview(req, 1L);

        // then
        assertThat(savedReviewId).isEqualTo(1L);
    }

    @DisplayName("코드 리뷰 등록 실패 - 일치하는 코드가 없음")
    @Test
    void insertReviewFailNotFoundCode() {
        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        doReturn(null).when(codesRepository).findByCodesId(any(Long.class));
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> reviewsService.insertReview(req, 1L)
        );

        assertEquals("일치하는 코드가 없습니다", exception.getMessage());
    }

    @DisplayName("코드 리뷰 상세 조회 성공")
    @Test
    void getReviewSucceed() {
        // given
        Long reviewId = 1L;
        Codes code = Codes.builder()
                .codesId(1L)
                .num(1L)
                .version(1)
                .title("개발새발 코드1")
                .codeWriter(user)
                .build();
        Reviews review = Reviews.builder()
                .reviewsId(1L)
                .codeContent("개발새발 리뷰")
                .content("hihi")
                .reReviews(List.of(new ReReviews()))
                .users(user)
                .codes(code)
                .build();
        ReviewLikes like = ReviewLikes.builder()
                .likesId(1L)
                .reviews(review)
                .users(user)
                .build();

        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(review).when(reviewsRepository).findByReviewsId(any(Long.class));
        doReturn(like).when(reviewLikesRepository).findByReviewsAndUsers(any(Reviews.class), any(Users.class));

        // when
        ReviewDetailRes success = reviewsService.getReview(reviewId, 0L);

        // then
        assertThat(success.getReviewId()).isEqualTo(1L);
        assertThat(success.getContent()).isEqualTo(review.getContent());
    }

    @DisplayName("코드 리뷰 수정 성공")
    @Test
    void updateReviewSucceed() {
        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(review).when(reviewsRepository).findByReviewsId(any(Long.class));

        // when
        int res = reviewsService.updateReview(req, 1L, 1L);

        // then
        assertThat(res).isEqualTo(1);
    }

    @DisplayName("코드 리뷰 수정 실패 - 일치하는 사용자가 없음")
    @Test
    void updateReviewFailNotFoundUser() {
        // given
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> reviewsService.updateReview(req, 1L, 1L)
        );

        assertEquals("일치하는 유저가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("코드 리뷰 수정 실패 - 일치하는 코드 리뷰가 없음")
    @Test
    void updateReviewFailNotFoundReview() {
        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(reviewsRepository).findByReviewsId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> reviewsService.updateReview(req, 1L, 1L)
        );

        assertEquals("일치하는 코드 리뷰가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("코드 리뷰 수정 실패 - 코드 리뷰 작성자와 일치하지 않는 경우")
    @Test
    void updateReviewFailNotMatched() {
        Users user2 = Users.builder()
                .usersId(2L)
                .build();

        Reviews review2 = Reviews.builder()
                .reviewsId(2L)
                .users(user2)
                .codes(code)
                .codeContent("hihihi")
                .build();

        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(review2).when(reviewsRepository).findByReviewsId(any(Long.class));

        // when & then
        NotMatchException exception = assertThrows(
                NotMatchException.class, () -> reviewsService.updateReview(req, 1L, 1L)
        );

        assertEquals("접근 권한이 없습니다", exception.getMessage());
    }

    @DisplayName("코드 리뷰 삭제 성공")
    @Test
    void deleteReviewSucceed() {
        Reviews reviews = Reviews.builder()
                .reviewsId(1L)
                .codeContent("!212")
                .content("설명설명")
                .build();
        ReReviews reReviews = ReReviews.builder()
                .reviews(reviews)
                .content("리리뷰")
                .build();
        ReReviewLikes reReviewLikes = ReReviewLikes.builder()
                .likesId(1L)
                .reReviews(reReviews)
                .users(user).build();

        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(review).when(reviewsRepository).findByReviewsId(any(Long.class));
        reviewLikesRepository.deleteAllByReviewId(any(Long.class));
        reReviewsRepository.deleteAllByReviews(any(Reviews.class));
        reReviewLikesRepository.deleteAllByReviewId(any(Long.class));

        // when
        int res = reviewsService.deleteReview(1L, 1L);

        // then
        assertThat(res).isEqualTo(1);
    }

    @DisplayName("코드 리뷰 삭제 실패 - 코드 리뷰 작성자와 삭제를 시도하는 사용자가 다름")
    @Test
    void deleteReviewFailUserDiffer() {
        // given
        doReturn(user2).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(review).when(reviewsRepository).findByReviewsId(any(Long.class));

        // when & then
        NotMatchException exception = assertThrows(
                NotMatchException.class, () -> reviewsService.deleteReview(1L, 1L)
        );

        assertEquals("접근 권한이 없습니다", exception.getMessage());
    }

    @DisplayName("코드 리뷰 좋아요 등록 성공")
    @Test
    void likeReviewSucceed() {
        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(review).when(reviewsRepository).findByReviewsId(any(Long.class));
        doReturn(null).when(reviewLikesRepository).findByReviewsAndUsers(any(Reviews.class), any(Users.class));

        int likeCnt = review.getLikeCnt();

        // when
        int res = reviewsService.likeReview(1L, 1L);

        // then
        assertThat(res).isEqualTo(review.getLikeCnt());
        assertThat(res).isEqualTo(likeCnt + 1);
    }

    @DisplayName("코드 리뷰 좋아요 취소 성공")
    @Test
    void likeReviewCancelSucceed() {
        ReviewLikes reviewLikes = ReviewLikes.builder()
                .reviews(review)
                .users(user).build();

        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(review).when(reviewsRepository).findByReviewsId(any(Long.class));
        doReturn(reviewLikes).when(reviewLikesRepository).findByReviewsAndUsers(any(Reviews.class), any(Users.class));

        int likeCnt = code.getLikeCnt();

        // when
        int res = reviewsService.likeReview(1L, 1L);

        // then
        assertThat(res).isEqualTo(review.getLikeCnt());
        assertThat(res).isEqualTo(likeCnt - 1);
    }

    @DisplayName("코드 리뷰 좋아요 등록 또는 취소 실패 - 일치하는 유저가 없음")
    @Test
    void likeReviewFailNotFoundUser() {
        // given
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> reviewsService.likeReview(1L, 0L)
        );

        assertEquals("일치하는 유저가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("코드 리뷰 좋아요 등록 또는 취소 실패 - 일치하는 코드가 없음")
    @Test
    void likeReviewFailNotFoundReview() {
        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(reviewsRepository).findByReviewsId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> reviewsService.likeReview(0L, 1L)
        );

        assertEquals("일치하는 코드 리뷰가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("코드 리뷰 신고 성공")
    @Test
    void complainReviewSucceed() {
        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(review).when(reviewsRepository).findByReviewsId(any(Long.class));

        // when
        int res = reviewsService.complainReview(1L, 1L);

        // then
        assertThat(res).isEqualTo(1);
    }

    @DisplayName("코드 리뷰 신고 실패 - 일치하는 유저가 없음")
    @Test
    void complainReviewFailNotFoundUser() {
        // given
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> reviewsService.complainReview(1L, 0L)
        );

        assertEquals("일치하는 유저가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("코드 리뷰 신고 실패 - 일치하는 코드가 없음")
    @Test
    void complainReviewFailNotFoundReview() {
        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(reviewsRepository).findByReviewsId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> reviewsService.complainReview(0L, 1L)
        );

        assertEquals("일치하는 코드 리뷰가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("코드 리뷰 신고 실패 - 이미 삭제된 코드 리뷰")
    @Test
    void complainReviewFailDeletedReview() {
        Reviews deletedReview = Reviews.builder()
                .reviewsId(3L)
                .codeContent("")
                .users(user)
                .build();

        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(deletedReview).when(reviewsRepository).findByReviewsId(any(Long.class));

        // when & then
        SelectedException exception = assertThrows(
                SelectedException.class, () -> reviewsService.complainReview(3L, 1L)
        );

        assertEquals("이미 삭제된 코드 리뷰입니다", exception.getMessage());
    }
}

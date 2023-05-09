package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.ReReviewReq;
import com.cody.roughcode.code.dto.res.ReReviewRes;
import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.entity.ReReviewLikes;
import com.cody.roughcode.code.entity.ReReviews;
import com.cody.roughcode.code.entity.Reviews;
import com.cody.roughcode.code.repository.ReReviewLikesRepository;
import com.cody.roughcode.code.repository.ReReviewsRepository;
import com.cody.roughcode.code.repository.ReviewsRepository;
import com.cody.roughcode.exception.NotMatchException;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.MessagingException;
import java.util.List;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class) // 가짜 객체 주입을 사용
public class ReReviewsServiceTest {
    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }

    @InjectMocks
    private ReReviewsServiceImpl reReviewsService;

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private ReviewsRepository reviewsRepository;
    @Mock
    private ReReviewsRepository reReviewsRepository;
    @Mock
    private ReReviewLikesRepository reReviewLikesRepository;

    final Users users = Users.builder()
            .usersId(1L)
            .email("kosy1782@gmail.com")
            .name("kosy318")
            .roles(List.of(String.valueOf(ROLE_USER)))
            .build();
    final Users users2 = Users.builder()
            .usersId(2L)
            .email("kosy1782@gmail.com")
            .name("kosy318")
            .roles(List.of(String.valueOf(ROLE_USER)))
            .build();

    final Codes code = Codes.builder()
                .codesId(1L)
                .codeWriter(users)
                .version(1)
                .num(1L)
                .build();

    final Reviews reviews = Reviews.builder()
            .reviewsId(1L)
            .codeContent("#include <iostream>")
            .content("굳")
            .lineNumbers("1,2")
            .codes(code)
            .build();

    final ReReviews reReviews = ReReviews.builder()
            .reReviewsId(1L)
            .reviews(reviews)
            .users(users)
            .content("리리뷰")
            .build();
    final ReReviews reReviews2 = ReReviews.builder()
            .reReviewsId(2L)
            .reviews(reviews)
            .users(users2)
            .content("리리뷰")
            .build();

    final ReReviewReq req = ReReviewReq.builder()
            .id(1L)
            .content("리리뷰")
            .build();

    @DisplayName("리-리뷰 수정 성공")
    @Test
    void updateReReviewSucceed() {
        // given
        doReturn(users2).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(reReviews).when(reReviewsRepository).findByReReviewsId(any(Long.class));

        // when & then
        NotMatchException exception = assertThrows(
                NotMatchException.class, () -> reReviewsService.updateReReview(req, 2L)
        );
        assertThat(exception.getMessage()).isEqualTo("접근 권한이 없습니다");
    }

    @DisplayName("리-리뷰 수정 실패 - 리리뷰가 내꺼가 아님")
    @Test
    void updateReReviewFailNotMatch() {
        // given
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(reReviews).when(reReviewsRepository).findByReReviewsId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> reReviewsService.updateReReview(req, 1L)
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 리뷰가 존재하지 않습니다");
    }

    @DisplayName("리-리뷰 수정 실패 - 리리뷰 x")
    @Test
    void updateReReviewFailNoReReview() {
        // given
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(reReviewsRepository).findByReReviewsId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> reReviewsService.updateReReview(req, 1L)
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 리뷰가 존재하지 않습니다");
    }

    @DisplayName("리-리뷰 수정 실패 - 유저 x")
    @Test
    void updateReReviewFailNoUser() {
        // given
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> reReviewsService.updateReReview(req, 1L)
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 유저가 존재하지 않습니다");
    }

    @DisplayName("리-리뷰 조회 성공 - with login")
    @Test
    void getReReviewListWithLoginSucceed() {
        // given
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(reviews).when(reviewsRepository).findByReviewsId(any(Long.class));
        doReturn(List.of(reReviews, reReviews)).when(reReviewsRepository).findAllByReviewsId(any(Long.class));
        doReturn(ReReviewLikes.builder().build()).when(reReviewLikesRepository).findByReReviewsAndUsers(any(ReReviews.class), any(Users.class));

        // when
        List<ReReviewRes> res = reReviewsService.getReReviewList(1L, -1L);

        // then
        assertThat(res.size()).isEqualTo(2);
    }

    @DisplayName("리-리뷰 조회 성공 - without login")
    @Test
    void getReReviewListWithoutLoginSucceed() {
        // given
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(reviews).when(reviewsRepository).findByReviewsId(any(Long.class));
        doReturn(List.of(reReviews, reReviews)).when(reReviewsRepository).findAllByReviewsId(any(Long.class));

        // when
        List<ReReviewRes> res = reReviewsService.getReReviewList(1L, -1L);

        // then
        assertThat(res.size()).isEqualTo(2);
    }


    @DisplayName("리-리뷰 등록 실패 - without login")
    @Test
    void insertReReviewFailNoReview() {
        // given
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(reviewsRepository).findByReviewsId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> reReviewsService.insertReReview(req, -1L)
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 리뷰가 존재하지 않습니다");
    }

    @DisplayName("리-리뷰 등록 성공 - without login")
    @Test
    void insertReReviewWithoutLoginSucceed() {
        // given
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(reviews).when(reviewsRepository).findByReviewsId(any(Long.class));
        doReturn(reReviews).when(reReviewsRepository).save(any(ReReviews.class));

        // when
        int res = reReviewsService.insertReReview(ReReviewReq.builder()
                .id(1L)
                .content("리리뷰")
                .build(), -1L);

        // then
        assertThat(res).isEqualTo(1);
    }

    @DisplayName("리-리뷰 등록 성공 - login")
    @Test
    void insertReReviewLoginSucceed() {
        // given
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(reviews).when(reviewsRepository).findByReviewsId(any(Long.class));
        doReturn(reReviews).when(reReviewsRepository).save(any(ReReviews.class));

        // when
        int res = reReviewsService.insertReReview(ReReviewReq.builder()
                .id(1L)
                .content("리리뷰")
                .build(), 1L);

        // then
        assertThat(res).isEqualTo(1);
    }

}

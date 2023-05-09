package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.ReReviewReq;
import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.entity.ReReviews;
import com.cody.roughcode.code.entity.Reviews;
import com.cody.roughcode.code.repository.ReReviewsRepository;
import com.cody.roughcode.code.repository.ReviewsRepository;
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

    final Users users = Users.builder()
            .usersId(1L)
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
            .content("리리뷰")
            .build();

    @DisplayName("리-리뷰 등록 성공 - without login")
    @Test
    void insertReReviewWithoutLoginSucceed() {
        // given
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(reviews).when(reviewsRepository).findByReviewsId(any(Long.class));
        doReturn(reReviews).when(reReviewsRepository).save(any(ReReviews.class));

        // when
        int res = reReviewsService.insertReReview(ReReviewReq.builder()
                .reviewId(1L)
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
                .reviewId(1L)
                .content("리리뷰")
                .build(), 1L);

        // then
        assertThat(res).isEqualTo(1);
    }

}

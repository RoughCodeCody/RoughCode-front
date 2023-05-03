package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.ReviewReq;
import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.entity.Reviews;
import com.cody.roughcode.code.repository.CodesRepository;
import com.cody.roughcode.code.repository.ReviewsRepository;
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
    private CodesRepository codesRepository;
    @Mock
    private UsersRepository usersRepository;

    final Users user = Users.builder()
            .usersId(1L)
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
            .content("설명설명")
            .codes(code)
            .users(user)
            .likeCnt(3)
            .complaint(2)
            .build();

    final ReviewReq req = ReviewReq.builder()
            .codeId(1L)
            .selectedRange(List.of(List.of(1,2), List.of(4, 6)))
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
}

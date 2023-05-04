package com.cody.roughcode.code.controller;

import com.cody.roughcode.code.dto.req.CodeReq;
import com.cody.roughcode.code.dto.req.ReviewReq;
import com.cody.roughcode.code.service.CodesService;
import com.cody.roughcode.code.service.ReviewsService;
import com.cody.roughcode.security.auth.JwtProperties;
import com.cody.roughcode.security.auth.JwtTokenProvider;
import com.cody.roughcode.user.entity.Users;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.Cookie;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class) // 가짜 객체 주입을 사용
public class ReviewsControllerTest {

    @InjectMocks
    private ReviewsController target;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach // 각각의 테스트가 실행되기 전에 초기화함
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .build();
    }

    @Mock
    ReviewsService reviewsService;
    @Mock
    JwtTokenProvider jwtTokenProvider;

    final String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzc2FmeTEyM0BnbWFpbC5jb20iLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjc0NzEyMDg2fQ.fMjhTvyLoCBzAXZ4gtJCAMS98j9DNsC7w2utcB-Uho";

    ReviewReq req = ReviewReq.builder()
            .codeId(1L)
            .selectedRange(List.of(List.of(1,2), List.of(4, 6)))
            .codeContent("hello world")
            .content("설명설명")
            .build();

    Users users = Users.builder()
            .usersId(1L)
            .email("cody306@gmail.com")
            .name("코디")
            .roles(List.of(String.valueOf(ROLE_USER)))
            .build();

    ReviewReq req2 = ReviewReq.builder()
            .codeId(1L)
            .selectedRange(List.of(List.of(3,5), List.of(6, 9)))
            .codeContent("hello world(modified)")
            .content("설명설명 수정")
            .build();

    @DisplayName("코드 리뷰 등록 성공")
    @Test
    public void insertReviewSucceed() throws Exception {
        // given
        final String url = "/api/v1/code/review";

        // ReviewsService insertReview 대한 stub 필요
        doReturn(1L).when(reviewsService)
                .insertReview(any(ReviewReq.class), any(Long.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(req))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 리뷰 등록 성공");
    }

    @DisplayName("코드 리뷰 등록 실패")
    @Test
    public void insertCodeFail() throws Exception {
        // given
        final String url = "/api/v1/code/review";

        // ReviewsService insertReview 대한 stub 필요
        doReturn(-1L).when(reviewsService)
                .insertReview(any(ReviewReq.class), any(Long.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(req))
        );

        // then
        // HTTP Status가 NotFound인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 리뷰 등록 실패");
    }

    @DisplayName("코드 정보 수정 성공")
    @Test
    public void updateReviewSucceed() throws Exception {
        // given
        final String url = "/api/v1/code/review/{reviewId}";

        // ReviewService updateReview 대한 stub 필요
        doReturn(1).when(reviewsService)
                .updateReview(any(ReviewReq.class), any(Long.class), any(Long.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(req2))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 리뷰 수정 성공");
    }

    @DisplayName("코드 정보 수정 실패")
    @Test
    public void updateCodeFail() throws Exception {
        // given
        final String url = "/api/v1/code/review/{reviewId}";

        // ReviewService updateReview 대한 stub 필요
        doReturn(0).when(reviewsService)
                .updateReview(any(ReviewReq.class), any(Long.class), any(Long.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(req))
        );

        // then
        // HTTP Status가 NotFound인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 리뷰 수정 실패");
    }

}

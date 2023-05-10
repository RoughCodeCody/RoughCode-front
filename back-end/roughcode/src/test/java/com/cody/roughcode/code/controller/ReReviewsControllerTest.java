package com.cody.roughcode.code.controller;

import com.cody.roughcode.code.dto.req.ReReviewReq;
import com.cody.roughcode.code.dto.res.ReReviewRes;
import com.cody.roughcode.code.service.ReReviewsServiceImpl;
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

@ExtendWith(MockitoExtension.class) // @WebMVCTest를 이용할 수도 있지만 속도가 느리다
public class ReReviewsControllerTest {
    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }

    @InjectMocks
    private ReReviewsController target;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach // 각각의 테스트가 실행되기 전에 초기화함
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .build();
    }

    final Users users = Users.builder()
            .usersId(1L)
            .email("kosy1782@gmail.com")
            .name("고수")
            .roles(List.of(String.valueOf(ROLE_USER)))
            .build();

    final String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzc2FmeTEyM0BnbWFpbC5jb20iLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjc0NzEyMDg2fQ.fMjhTvyLoCBzAXZ4gtJCAMS98j9DNsC7w2utcB-Uho";

    final ReReviewReq req = ReReviewReq.builder()
            .id(1L)
            .content("리리뷰")
            .build();

    @Mock
    private ReReviewsServiceImpl reReviewsService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("리-리뷰 신고 성공")
    @Test
    public void reReviewComplainSucceed() throws Exception {
        // given
        final String url = "/api/v1/code/rereview/{reReviewId}/complaint";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(1).when(reReviewsService)
                .reReviewComplain(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url, 1)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 리-리뷰 신고 성공");
    }

    @DisplayName("리-리뷰 신고 실패")
    @Test
    public void reReviewComplainFail() throws Exception {
        // given
        final String url = "/api/v1/code/rereview/{reReviewId}/complaint";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(0).when(reReviewsService)
                .reReviewComplain(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url, 1)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 리-리뷰 신고 실패");
    }

    @DisplayName("코드 리-리뷰 좋아요/취소 실패")
    @Test
    public void likeReReviewFail() throws Exception {
        // given
        final String url = "/api/v1/code/rereview/{reReviewId}/like";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(-1).when(reReviewsService).likeReReview(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 리-리뷰 좋아요 또는 취소 실패");
    }

    @DisplayName("코드 리-리뷰 좋아요 취소 성공")
    @Test
    public void likeReReviewSucceedCancel() throws Exception {
        // given
        final String url = "/api/v1/code/rereview/{reReviewId}/like";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(0).when(reReviewsService).likeReReview(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        String result = jsonObject.get("result").getAsString();
        assertThat(message).isEqualTo("코드 리-리뷰 좋아요 또는 취소 성공");
        assertThat(result).isEqualTo("0");
    }

    @DisplayName("코드 리-리뷰 좋아요 성공")
    @Test
    public void likeReReviewSucceedLike() throws Exception {
        // given
        final String url = "/api/v1/code/rereview/{reReviewId}/like";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(1).when(reReviewsService).likeReReview(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        String result = jsonObject.get("result").getAsString();
        assertThat(message).isEqualTo("코드 리-리뷰 좋아요 또는 취소 성공");
        assertThat(result).isEqualTo("1");
    }

    @DisplayName("코드 리-리뷰 삭제 성공")
    @Test
    public void deleteReReviewSucceed() throws Exception {
        // given
        final String url = "/api/v1/code/rereview/{reReviewId}";

        doReturn(1).when(reReviewsService).deleteReReview(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 리-리뷰 삭제 성공");
    }

    @DisplayName("코드 리-리뷰 삭제 실패")
    @Test
    public void deleteReReviewFail() throws Exception {
        // given
        final String url = "/api/v1/code/rereview/{reReviewId}";

        doReturn(0).when(reReviewsService).deleteReReview(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 리-리뷰 삭제 실패");
    }

    @DisplayName("코드 리-리뷰 수정 실패")
    @Test
    public void updateReReviewFail() throws Exception {
        // given
        final String url = "/api/v1/code/rereview";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(0).when(reReviewsService).updateReReview(any(ReReviewReq.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(req))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 리-리뷰 수정 실패");
    }

    @DisplayName("코드 리-리뷰 수정 성공")
    @Test
    public void updateReReviewSucceed() throws Exception {
        // given
        final String url = "/api/v1/code/rereview";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(1).when(reReviewsService).updateReReview(any(ReReviewReq.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
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
        assertThat(message).isEqualTo("코드 리-리뷰 수정 성공");
    }

    @DisplayName("코드 리-리뷰 조회 성공 - with cookie")
    @Test
    public void getReReviewListWithCookieSucceed() throws Exception {
        // given
        final String url = "/api/v1/code/rereview/{reviewId}";

        doReturn(List.of(ReReviewRes.builder().build())).when(reReviewsService).getReReviewList(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 리-리뷰 조회 성공");
    }

    @DisplayName("코드 리-리뷰 조회 성공 - without cookie")
    @Test
    public void getReReviewListWithoutCookieSucceed() throws Exception {
        // given
        final String url = "/api/v1/code/rereview/{reviewId}";

        doReturn(List.of(ReReviewRes.builder().build())).when(reReviewsService).getReReviewList(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url, 1L)
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 리-리뷰 조회 성공");
    }

    @DisplayName("코드 리-리뷰 달기 성공 - without cookie")
    @Test
    public void insertReReviewWithoutCookieSucceed() throws Exception {
        // given
        final String url = "/api/v1/code/rereview";

        doReturn(1).when(reReviewsService).insertReReview(any(ReReviewReq.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(req))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        String result = jsonObject.get("result").getAsString();
        assertThat(message).isEqualTo("코드 리-리뷰 등록 성공");
        assertThat(result).isEqualTo("1");
    }

    @DisplayName("코드 리-리뷰 달기 성공 - with cookie")
    @Test
    public void insertReReviewWithCookieSucceed() throws Exception {
        // given
        final String url = "/api/v1/code/rereview";

        doReturn(1).when(reReviewsService).insertReReview(any(ReReviewReq.class), any(Long.class));

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
        String result = jsonObject.get("result").getAsString();
        assertThat(message).isEqualTo("코드 리-리뷰 등록 성공");
        assertThat(result).isEqualTo("1");
    }

}
package com.cody.roughcode.user.controller;

import com.cody.roughcode.security.auth.JwtProperties;
import com.cody.roughcode.security.auth.JwtTokenProvider;
import com.cody.roughcode.security.auth.TokenInfo;
import com.cody.roughcode.security.dto.TokenReq;
import com.cody.roughcode.security.handler.CustomLogoutHandler;
import com.cody.roughcode.user.dto.req.UserReq;
import com.cody.roughcode.user.dto.res.UserRes;
import com.cody.roughcode.user.service.JwtServiceImpl;
import com.cody.roughcode.user.service.UsersServiceImpl;
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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @InjectMocks
    private UsersController target;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .build();
    }

    final String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzc2FmeTEyM0BnbWFpbC5jb20iLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjc0NzEyMDg2fQ.fMjhTvyLoCBzAXZ4gtJCAMS98j9DNsC7w2utcB-Uho";
    final String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODI5OTE1OTN9.7zVQxB2iFNSqv4at9fFsXDoBO1bB6OBhNJoa_6aNOPY";

    UserReq userReq = UserReq.builder()
            .nickname("코디코디")
            .email("cody306@roughcode.com")
            .build();

    UserRes userRes = UserRes.builder()
            .nickname("코디")
            .email("cody306@gmail.com")
            .nickname("코디")
            .projectsCnt(3L)
            .codesCnt(5L)
            .build();

    TokenInfo tokenInfo = new TokenInfo(accessToken, refreshToken, 1L);
    TokenReq tokenReq = new TokenReq(accessToken, refreshToken);

    @Mock
    private UsersServiceImpl usersService;
    @Mock
    private JwtServiceImpl jwtService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private RedisTemplate<String, Object> redisTemplate;
    @Mock
    private TestRestTemplate restTemplate;
    @Mock
    private CustomLogoutHandler customLogoutHandler;

    @DisplayName("회원 정보 조회 성공")
    @Test
    public void selectOneUserSucceed() throws Exception {
        // given
        final String url = "/api/v1/user";
        doReturn(userRes).when(usersService).selectOneUser(any(Long.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("회원 정보 조회 성공");

    }

    @DisplayName("회원 정보 수정 성공")
    @Test
    public void updateUserSucceed() throws Exception {

        // given
        final String url = "/api/v1/user";
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(userReq))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("회원 정보 수정 성공");
    }


    @DisplayName("닉네임 중복 체크 성공")
    @Test
    public void checkNicknameSucceed() throws Exception {

        // given
        final String url = "/api/v1/user/nicknameCheck";
        doReturn(true).when(usersService).checkNickname(any(String.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .param("nickname", "코디")
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("중복된 닉네임입니다. 다른 닉네임을 입력해주세요.");
    }

    @DisplayName("토큰 재발급 성공")
    @Test
    public void reissueTokenSucceed() throws Exception {
        // given
        String url = "/api/v1/user/token";
        doReturn(tokenInfo).when(jwtService).reissue(any(HttpServletRequest.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                post(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("Token 재발급 성공");
    }

}
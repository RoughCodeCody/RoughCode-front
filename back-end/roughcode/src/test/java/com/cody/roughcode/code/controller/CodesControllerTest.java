package com.cody.roughcode.code.controller;

import com.cody.roughcode.code.dto.req.CodeReq;
import com.cody.roughcode.code.service.CodesService;
import com.cody.roughcode.security.auth.JwtProperties;
import com.cody.roughcode.security.auth.JwtTokenProvider;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CodesControllerTest {

    @InjectMocks
    private CodesController target;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach // 각각의 테스트가 실행되기 전에 초기화함
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .build();
    }

    @Mock
    CodesService codesService;
    @Mock
    JwtTokenProvider jwtTokenProvider;

    final String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzc2FmeTEyM0BnbWFpbC5jb20iLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjc0NzEyMDg2fQ.fMjhTvyLoCBzAXZ4gtJCAMS98j9DNsC7w2utcB-Uho";

    final CodeReq req = CodeReq.builder()
            .codeId(1L)
            .title("개발새발 코드")
            .selectedTagsId(List.of(1L))
            .githubUrl("https://api.github.com/repos/cody/hello-world/contents/src/main.py")
            .content("시간초과 뜹니다")
            .projectId(1L)
            .build();

    @DisplayName("코드 정보 등록 성공")
    @Test
    public void insertCodeSucceed() throws Exception {
        // given
        final String url = "/api/v1/code";

        // CodeService insertCode 대한 stub 필요
        doReturn(1L).when(codesService)
                .insertCode(any(CodeReq.class), any(Long.class));
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
        assertThat(message).isEqualTo("코드 정보 등록 성공");
    }

    @DisplayName("코드 정보 등록 실패")
    @Test
    public void insertCodeFail() throws Exception {
        // given
        final String url = "/api/v1/code";

        // CodeService insertCode 대한 stub 필요
        doReturn(-1L).when(codesService)
                .insertCode(any(CodeReq.class), any(Long.class));
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
        assertThat(message).isEqualTo("코드 정보 등록 실패");
    }
}
package com.cody.roughcode.mypage.controller;

import com.cody.roughcode.alarm.entity.Alarm;
import com.cody.roughcode.alarm.service.AlarmServiceImpl;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.Cookie;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MypageControllerTest {
    static{
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }

    @InjectMocks
    private MypageController target;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init(){
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .build();
    }

    final String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzc2FmeTEyM0BnbWFpbC5jb20iLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjc0NzEyMDg2fQ.fMjhTvyLoCBzAXZ4gtJCAMS98j9DNsC7w2utcB-Uho";

    final Alarm alarm1 = Alarm.builder()
            .createdDate(LocalDateTime.now())
            .postId(1L)
            .section("project")
            .content(List.of("test", "project1", "test"))
            .userId(1L)
            .build();
    final Alarm alarm2 = Alarm.builder()
            .createdDate(LocalDateTime.now())
            .postId(2L)
            .section("project")
            .content(List.of("test", "project2", "test"))
            .userId(1L)
            .build();

    final Alarm alarm3 = Alarm.builder()
            .createdDate(LocalDateTime.now())
            .postId(1L)
            .section("project")
            .content(List.of("test", "project1", "test"))
            .userId(2L)
            .build();

    @Mock
    private AlarmServiceImpl alarmService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("알람 삭제 성공")
    @Test
    public void deleteAlarmSucceed() throws Exception {
        // given
        final String url = "/api/v1/mypage/alarm/{alarmId}";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url, "60a957bcf77ec21e1aa72c93")
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("알림 삭제 성공");
    }

    @DisplayName("알람 목록 조회 성공")
    @Test
    public void getAlarmListSucceed() throws Exception {
        // given
        final String url = "/api/v1/mypage/alarm";
        doReturn(List.of(alarm1, alarm2)).when(alarmService).getAlarmList(any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("알림 조회 성공");
    }
}

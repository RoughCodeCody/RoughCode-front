package com.cody.roughcode.mypage.controller;

import com.cody.roughcode.alarm.entity.Alarm;
import com.cody.roughcode.alarm.service.AlarmServiceImpl;
import com.cody.roughcode.code.dto.res.CodeInfoRes;
import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.mypage.service.MypageServiceImpl;
import com.cody.roughcode.project.dto.res.ProjectInfoRes;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.security.auth.JwtProperties;
import com.cody.roughcode.security.auth.JwtTokenProvider;
import com.cody.roughcode.user.entity.Users;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
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

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
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

    final Users users = Users.builder()
            .usersId(1L)
            .email("kosy1782@gmail.com")
            .name("kosy318")
            .roles(List.of(String.valueOf(ROLE_USER)))
            .build();

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
    private MypageServiceImpl mypageService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    final Codes code = Codes.builder()
            .codesId(1L)
            .num(1L)
            .version(1)
            .codeWriter(users)
            .title("title")
            .reviewCnt(1)
            .build();

    @DisplayName("리뷰한 코드 목록 조회 성공")
    @Test
    public void getReviewCodeListSucceed() throws Exception {
        // given
        final String url = "/api/v1/mypage/code/review";

        int page = 10;

        List<CodeInfoRes> codeInfoRes = List.of(
                CodeInfoRes.builder()
                        .codeId(code.getCodesId())
                        .version(code.getVersion())
                        .title(code.getTitle())
                        .date(code.getModifiedDate())
                        .likeCnt(code.getLikeCnt())
                        .reviewCnt(code.getReviewCnt())
                        .userName(code.getCodeWriter().getName())
                        .build()
        );

        doReturn(Pair.of(codeInfoRes, false)).when(mypageService)
                .getReviewCodeList(any(PageRequest.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .param("page", String.valueOf(page))
                        .param("size", "10")
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("리뷰한 코드 목록 조회 성공");
    }

    @DisplayName("코드 목록 조회 성공")
    @Test
    public void getCodeListSucceed() throws Exception {
        // given
        final String url = "/api/v1/mypage/code";

        int page = 10;

        List<CodeInfoRes> codeInfoRes = List.of(
                CodeInfoRes.builder()
                        .codeId(code.getCodesId())
                        .version(code.getVersion())
                        .title(code.getTitle())
                        .date(code.getModifiedDate())
                        .likeCnt(code.getLikeCnt())
                        .reviewCnt(code.getReviewCnt())
                        .userName(code.getCodeWriter().getName())
                        .build()
        );
        doReturn(Pair.of(codeInfoRes, false)).when(mypageService)
                .getCodeList(any(PageRequest.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .param("page", String.valueOf(page))
                        .param("size", "10")
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("내 코드 목록 조회 성공");
    }

    @DisplayName("피드백한 프로젝트 목록 조회 성공")
    @Test
    public void getFeedbackProjectListSucceed() throws Exception {
        // given
        final String url = "/api/v1/mypage/project/feedback";

        int page = 10;

        final Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .build();
        List<ProjectInfoRes> projectInfoRes = List.of(
                ProjectInfoRes.builder()
                        .date(project.getModifiedDate())
                        .img(project.getImg())
                        .projectId(project.getProjectsId())
                        .feedbackCnt(project.getFeedbackCnt())
                        .introduction(project.getIntroduction())
                        .likeCnt(project.getLikeCnt())
                        .tags(List.of("springboot"))
                        .title(project.getTitle())
                        .version(project.getVersion())
                        .build()
        );
        doReturn(Pair.of(projectInfoRes, false)).when(mypageService)
                .getFeedbackProjectList(any(PageRequest.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .param("page", String.valueOf(page))
                        .param("size", "10")
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("피드백한 프로젝트 목록 조회 성공");
    }

    @DisplayName("내 즐겨찾기 프로젝트 목록 조회 성공")
    @Test
    public void getFavoriteProjectListSucceed() throws Exception {
        // given
        final String url = "/api/v1/mypage/project/favorite";

        int page = 10;

        final Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .build();
        List<ProjectInfoRes> projectInfoRes = List.of(
                ProjectInfoRes.builder()
                        .date(project.getModifiedDate())
                        .img(project.getImg())
                        .projectId(project.getProjectsId())
                        .feedbackCnt(project.getFeedbackCnt())
                        .introduction(project.getIntroduction())
                        .likeCnt(project.getLikeCnt())
                        .tags(List.of("springboot"))
                        .title(project.getTitle())
                        .version(project.getVersion())
                        .build()
        );
        doReturn(Pair.of(projectInfoRes, false)).when(mypageService)
                .getFavoriteProjectList(any(PageRequest.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .param("page", String.valueOf(page))
                        .param("size", "10")
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("내 즐겨찾기 프로젝트 목록 조회 성공");
    }

    @DisplayName("프로젝트 목록 조회 성공")
    @Test
    public void getProjectListSucceed() throws Exception {
        // given
        final String url = "/api/v1/mypage/project";

        int page = 10;

        final Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .build();
        List<ProjectInfoRes> projectInfoRes = List.of(
                ProjectInfoRes.builder()
                        .date(project.getModifiedDate())
                        .img(project.getImg())
                        .projectId(project.getProjectsId())
                        .feedbackCnt(project.getFeedbackCnt())
                        .introduction(project.getIntroduction())
                        .likeCnt(project.getLikeCnt())
                        .tags(List.of("springboot"))
                        .title(project.getTitle())
                        .version(project.getVersion())
                        .build()
        );
        doReturn(Pair.of(projectInfoRes, false)).when(mypageService)
                .getProjectList(any(PageRequest.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .param("page", String.valueOf(page))
                        .param("size", "10")
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("내 프로젝트 목록 조회 성공");
    }

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

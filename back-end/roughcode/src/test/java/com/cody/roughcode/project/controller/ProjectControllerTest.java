package com.cody.roughcode.project.controller;

import com.cody.roughcode.project.dto.req.ProjectReq;
import com.cody.roughcode.project.service.ProjectsService;
import com.cody.roughcode.project.service.ProjectsServiceImpl;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class) // @WebMVCTest를 이용할 수도 있지만 속도가 느리다
public class ProjectControllerTest {

    @InjectMocks
    private ProjectsController target;

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

    @Mock
    private ProjectsServiceImpl projectsService;
    String email = "kosy1782@gmail.com";

    @DisplayName("프로젝트 등록 성공")
    @Test
    public void insertProjectSucceed() throws Exception {
        // given
        final String url = "/api/v1/project";

        ProjectReq req = ProjectReq.builder()
                .codesId((long) -1)
                .projectId((long) -1)
                .title("title")
                .url("https://www.google.com")
                .introduction("introduction")
                .img("https://www.linkpicture.com/q/KakaoTalk_20230413_101644169.png")
                .selectedTagsId(List.of(1L))
                .content("content")
                .notice("notice")
                .build();

        // ProjectService insertProject 대한 stub필요
        doReturn(1).when(projectsService)
                .insertProject(any(ProjectReq.class), any(Long.class));

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
        assertThat(message).isEqualTo("프로젝트 등록 성공");
    }

    @DisplayName("프로젝트 등록 실패")
    @Test
    public void insertProjectFail() throws Exception {
        // given
        final String url = "/api/v1/project";

        ProjectReq req = ProjectReq.builder()
                .codesId((long) -1)
                .projectId((long) -1)
                .title("title")
                .url("https://www.google.com")
                .introduction("introduction")
                .img("https://www.linkpicture.com/q/KakaoTalk_20230413_101644169.png")
                .selectedTagsId(List.of(1L))
                .content("content")
                .notice("notice")
                .build();

        // ProjectService insertProject 대한 stub필요
        doReturn(0).when(projectsService)
                .insertProject(any(ProjectReq.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(req))
        );


        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 등록 실패");
    }

}

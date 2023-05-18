package com.cody.roughcode.project.controller;

import com.cody.roughcode.project.dto.req.FeedbackInsertReq;
import com.cody.roughcode.project.dto.req.FeedbackUpdateReq;
import com.cody.roughcode.project.dto.res.FeedbackInfoRes;
import com.cody.roughcode.project.dto.res.ProjectDetailRes;
import com.cody.roughcode.project.dto.res.ProjectInfoRes;
import com.cody.roughcode.project.dto.req.ProjectReq;
import com.cody.roughcode.project.dto.res.ProjectTagsRes;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.service.ProjectsServiceImpl;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class) // @WebMVCTest를 이용할 수도 있지만 속도가 느리다
public class ProjectControllerTest {
    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }

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

    final String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzc2FmeTEyM0BnbWFpbC5jb20iLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjc0NzEyMDg2fQ.fMjhTvyLoCBzAXZ4gtJCAMS98j9DNsC7w2utcB-Uho";

    final ProjectReq req = ProjectReq.builder()
            .projectId((long) -1)
            .title("title")
            .url("https://www.google.com")
            .introduction("introduction")
            .selectedTagsId(List.of(1L))
            .content("content")
            .notice("notice")
            .build();

    private static MockMultipartFile getImage() throws IOException {
        File imageFile = new File("src/test/java/com/cody/roughcode/resources/image/A306_ERD (2).png");
        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
        MockMultipartFile thumbnail = new MockMultipartFile(
                "thumbnail",
                "A306_ERD (2).png",
                MediaType.IMAGE_PNG_VALUE,
                imageBytes
        );
        return thumbnail;
    }


    private List<ProjectTagsRes> tagsResInit() {
        List<String> list = List.of("SpringBoot", "React", "AWS");
        List<ProjectTagsRes> tagsList = new ArrayList<>();
        for (long i = 1L; i <= 3L; i++) {
            tagsList.add(ProjectTagsRes.builder()
                    .tagId(i)
                    .name(list.get((int)i-1))
                    .cnt(0)
                    .build());
        }

        return tagsList;
    }

    @Mock
    private ProjectsServiceImpl projectsService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private RedisTemplate<String, String> redisTemplate;
    @Mock
    private ValueOperations operations;

    @DisplayName("프로젝트 닫기 성공")
    @Test
    public void closeProjectSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/close";
        final Long projectId = 1L;

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(1).when(projectsService).closeProject(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url, projectId)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 닫기 성공");
    }

    @DisplayName("프로젝트 닫기 실패")
    @Test
    public void closeProjectFail() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/close";
        final Long projectId = 1L;

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(0).when(projectsService).closeProject(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url, projectId)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 닫기 실패");
    }

    @DisplayName("프로젝트 열기 성공")
    @Test
    public void openProjectSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/open";
        final Long projectId = 1L;

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(1).when(projectsService).openProject(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url, projectId)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 열기 성공");
    }

    @DisplayName("프로젝트 열기 실패")
    @Test
    public void openProjectFail() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/open";
        final Long projectId = 1L;

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(0).when(projectsService).openProject(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url, projectId)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 열기 실패");
    }

    @DisplayName("이미지 삭제 성공")
    @Test
    public void deleteImageSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/image";
        final Long projectId = 1L;
        String imgUrl = "https://roughcode.s3.ap-northeast-2.amazonaws.com/project/content/kosy318_1_2_2023-05-01%2014%3A29%3A07";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(1).when(projectsService).deleteImage(any(String.class), any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url, projectId)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(imgUrl)
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("이미지 삭제 성공");
    }

    @DisplayName("이미지 삭제 실패")
    @Test
    public void deleteImageFail() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/image";
        final Long projectId = 1L;
        String imgUrl = "https://roughcode.s3.ap-northeast-2.amazonaws.com/project/content/kosy318_1_2_2023-05-01%2014%3A29%3A07";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(0).when(projectsService).deleteImage(any(String.class), any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url, projectId)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(imgUrl)
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("이미지 삭제 실패");
    }

    @DisplayName("이미지 등록 성공")
    @Test
    public void insertImageSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/image";
        final Long projectId = 1L;
        MockMultipartFile image = getImage();

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn("imageUrl").when(projectsService).insertImage(any(MultipartFile.class), any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.multipart(url, projectId)
                        .file("image", image.getBytes())
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("이미지 등록 성공");
    }

    @DisplayName("이미지 등록 실패")
    @Test
    public void insertImageFail() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/image";
        final Long projectId = 1L;
        MockMultipartFile image = getImage();

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(null).when(projectsService).insertImage(any(MultipartFile.class), any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.multipart(url, projectId)
                        .file("image", image.getBytes())
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("이미지 등록 실패");
    }

    @DisplayName("피드백 좋아요 또는 취소 성공")
    @Test
    public void likeProjectFeedbackSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/feedback/{feedbackId}/like";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(1).when(projectsService)
                .likeProjectFeedback(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("피드백 좋아요 또는 취소 성공");
    }

    @DisplayName("피드백 좋아요 또는 취소 실패")
    @Test
    public void likeProjectFeedbackFail() throws Exception {
        // given
        final String url = "/api/v1/project/feedback/{feedbackId}/like";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(-1).when(projectsService)
                .likeProjectFeedback(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("피드백 좋아요 또는 취소 실패");
    }

    @DisplayName("프로젝트 즐겨찾기 등록 또는 취소 성공")
    @Test
    public void favoriteProjectSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/favorite";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(1).when(projectsService)
                .favoriteProject(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson("content"))
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 즐겨찾기 등록 또는 취소 성공");
    }

    @DisplayName("프로젝트 즐겨찾기 등록 또는 취소 실패")
    @Test
    public void favoriteProjectFail() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/favorite";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(-1).when(projectsService)
                .favoriteProject(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson("content"))
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 즐겨찾기 등록 또는 취소 실패");
    }

    @DisplayName("프로젝트 좋아요 또는 취소 성공")
    @Test
    public void likeProjectSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/like";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(1).when(projectsService)
                .likeProject(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 좋아요 또는 취소 성공");
    }

    @DisplayName("프로젝트 좋아요 또는 취소 실패")
    @Test
    public void likeProjectFail() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/like";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(-1).when(projectsService)
                .likeProject(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 좋아요 또는 취소 실패");
    }

    @DisplayName("프로젝트 열림 확인 성공")
    @Test
    public void isProjectOpenSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/check/{projectId}";

        doReturn(1).when(projectsService)
                .isProjectOpen(any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url, 1L)
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 열림 확인 성공");
    }

    @DisplayName("프로젝트 열림 확인 실패")
    @Test
    public void isProjectOpenFail() throws Exception {
        // given
        final String url = "/api/v1/project/check/{projectId}";

        doReturn(-2).when(projectsService)
                .isProjectOpen(any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url, 1L)
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 열림 확인 실패");
    }

    @DisplayName("피드백 신고 성공")
    @Test
    public void feedbackComplainSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/feedback/{feedbackId}/complaint";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(1).when(projectsService)
                .feedbackComplain(any(Long.class), any(Long.class));

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
        assertThat(message).isEqualTo("프로젝트 피드백 신고 성공");
    }

    @DisplayName("피드백 신고 실패")
    @Test
    public void feedbackComplainFail() throws Exception {
        // given
        final String url = "/api/v1/project/feedback/{feedbackId}/complaint";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(-1).when(projectsService)
                .feedbackComplain(any(Long.class), any(Long.class));

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
        assertThat(message).isEqualTo("프로젝트 피드백 신고 실패");
    }
    
    @DisplayName("tag 목록 검색 실패")
    @Test
    public void searchTagsFail() throws Exception {
        // given
        final String url = "/api/v1/project/tag";

        doThrow(new NullPointerException()).when(projectsService).searchTags("");

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .queryParam("keyword", "")
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isBadRequest()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 태그 목록 조회 실패");
    }

    @DisplayName("tag 목록 검색 성공")
    @Test
    public void searchTags() throws Exception {
        // given
        final String url = "/api/v1/project/tag";

        List<ProjectTagsRes> tagList = tagsResInit();
        doReturn(tagList).when(projectsService).searchTags("");

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .queryParam("keyword", "")
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 태그 목록 조회 성공");
    }

    @DisplayName("url 체크 성공 - safe")
    @Test
    public void checkProjectSucceedSafe() throws Exception {
        // given
        final String url = "/api/v1/project/check";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(true).when(projectsService)
                .checkProject(any(String.class), eq(false));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .param("url", url)
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 검사 성공");
        String result = jsonObject.get("result").getAsString();
        assertThat(result).isEqualTo("true");
    }

    @DisplayName("url 체크 성공 - not safe")
    @Test
    public void checkProjectSucceedNotSafe() throws Exception {
        // given
        final String url = "/api/v1/project/check";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(false).when(projectsService).checkProject(any(String.class), eq(false));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .param("url", url)
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 검사 성공");
        String result = jsonObject.get("result").getAsString();
        assertThat(result).isEqualTo("false");
    }

    @DisplayName("피드백 삭제 성공")
    @Test
    public void deleteFeedbackSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/feedback/{feedbackId}";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(1).when(projectsService)
                .deleteFeedback(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url, 1)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("피드백 삭제 성공");
    }

    @DisplayName("피드백 삭제 실패")
    @Test
    public void deleteFeedbackFail() throws Exception {
        // given
        final String url = "/api/v1/project/feedback/{feedbackId}";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(0).when(projectsService)
                .deleteFeedback(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url, 1)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("피드백 삭제 실패");
    }

    @DisplayName("피드백 삭제 실패 - 이미 채택된 피드백")
    @Test
    public void deleteFeedbackFailAlreadySelected() throws Exception {
        // given
        final String url = "/api/v1/project/feedback/{feedbackId}";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doThrow(new ResponseStatusException(HttpStatus.CONFLICT, "채택된 피드백은 삭제할 수 없습니다"))
                .when(projectsService)
                .deleteFeedback(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url, 1)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isConflict()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("채택된 피드백은 삭제할 수 없습니다");
    }

    @DisplayName("피드백 목록 조회 성공")
    @Test
    public void getFeedbackListSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/feedback";

        FeedbackInfoRes feedbackInfoRes = FeedbackInfoRes.builder()
                .feedbackId(1L)
                .userName("user")
                .userId(1L)
                .content("content")
                .selected(true)
                .build();

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(List.of(feedbackInfoRes)).when(projectsService)
                .getFeedbackList(any(Long.class), any(Long.class), any(Boolean.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url, 1)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("피드백 목록 조회 성공");
    }

    @DisplayName("피드백 목록 조회 실패")
    @Test
    public void getFeedbackListFail() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/feedback";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(null).when(projectsService)
                .getFeedbackList(any(Long.class), any(Long.class), any(Boolean.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url, 1)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("피드백 목록 조회 실패");
    }

    @DisplayName("피드백 수정 성공")
    @Test
    public void updateFeedbackSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/feedback";

        FeedbackUpdateReq req = FeedbackUpdateReq.builder()
                .content("개발새발 최고")
                .feedbackId(1L)
                .build();

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(true).when(projectsService)
                .updateFeedback(any(FeedbackUpdateReq.class), any(Long.class));

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
        assertThat(message).isEqualTo("피드백 수정 성공");
    }

    @DisplayName("피드백 수정 실패 - 이미 채택된 피드백")
    @Test
    public void updateFeedbackFailAlreadySelected() throws Exception {
        // given
        final String url = "/api/v1/project/feedback";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doThrow(new ResponseStatusException(HttpStatus.CONFLICT, "채택된 피드백은 수정할 수 없습니다"))
                .when(projectsService)
                .updateFeedback(any(FeedbackUpdateReq.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(req))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isConflict()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("채택된 피드백은 수정할 수 없습니다");
    }

    @DisplayName("피드백 수정 실패")
    @Test
    public void updateFeedbackFail() throws Exception {
        // given
        final String url = "/api/v1/project/feedback";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(false).when(projectsService)
                .updateFeedback(any(FeedbackUpdateReq.class), any(Long.class));

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
        assertThat(message).isEqualTo("피드백 수정 실패");
    }

    @DisplayName("피드백 등록 성공")
    @Test
    public void insertFeedbackSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/feedback";

        FeedbackInsertReq req = FeedbackInsertReq.builder()
                .content("개발새발 최고")
                .projectId(1L)
                .build();

        doReturn(1).when(projectsService)
                .insertFeedback(any(FeedbackInsertReq.class), any(Long.class));

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
        assertThat(message).isEqualTo("피드백 등록 성공");
    }

    @DisplayName("피드백 등록 실패")
    @Test
    public void insertFeedbackFail() throws Exception {
        // given
        final String url = "/api/v1/project/feedback";

        doReturn(0).when(projectsService)
                .insertFeedback(any(FeedbackInsertReq.class), any(Long.class));


        FeedbackInsertReq req = FeedbackInsertReq.builder()
                .content("개발새발 최고")
                .projectId(1L)
                .build();

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
        assertThat(message).isEqualTo("피드백 등록 실패");
    }

    @DisplayName("프로젝트 상세 조회 성공")
    @Test
    public void getProjectSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}";

        doReturn(new ProjectDetailRes()).when(projectsService)
                .getProject(any(Long.class), any(Long.class));

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
        assertThat(message).isEqualTo("프로젝트 상세 조회 성공");
    }

    @DisplayName("프로젝트 상세 조회 실패")
    @Test
    public void getProjectFail() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}";

        doReturn(null).when(projectsService)
                .getProject(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url, 1L)
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 상세 조회 실패");
    }

    @DisplayName("프로젝트 목록 조회 성공")
    @Test
    public void getProjectListSucceed() throws Exception {
        // given
        final String url = "/api/v1/project";

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
                        .date(project.getCreatedDate())
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
        doReturn(Pair.of(projectInfoRes, false)).when(projectsService)
                .getProjectList(any(String.class), any(PageRequest.class), any(String.class), any(String.class), any(int.class));

        String keyword = "title";
        String tagIds = "2";
        int closed = 0;

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .param("sort", "createdDate")
                        .param("page", String.valueOf(page))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(req))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 목록 조회 성공");
    }

    @DisplayName("프로젝트 목록 조회 withdout sort and page")
    @Test
    public void getProjectListSucceedWithNoParams() throws Exception {
        // given
        final String url = "/api/v1/project";
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
                        .date(project.getCreatedDate())
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
        doReturn(Pair.of(projectInfoRes, false)).when(projectsService)
                .getProjectList(any(String.class), any(PageRequest.class), any(String.class), any(String.class), any(int.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(req))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 목록 조회 성공");
    }

    @DisplayName("프로젝트 삭제 성공")
    @Test
    public void deleteSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(1).when(projectsService)
                .putExpireDateProject(any(Long.class), any(Long.class));

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
        assertThat(message).isEqualTo("프로젝트 삭제 성공");
    }

    @DisplayName("프로젝트 삭제 실패")
    @Test
    public void deleteFail() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}";

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(0).when(projectsService)
                .putExpireDateProject(any(Long.class), any(Long.class));

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
        assertThat(message).isEqualTo("프로젝트 삭제 실패");
    }

    @DisplayName("프로젝트 코드 연결 성공")
    @Test
    public void connectSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/connect";
        List<Long> req = List.of(0L, 1L, 2L);

        doReturn(req.size()).when(projectsService)
                .connect(any(Long.class), any(Long.class), any(List.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url, 1L)
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
        assertThat(message).isEqualTo("프로젝트 코드 연결 성공");
        String result = jsonObject.get("result").getAsString();
        assertThat(result).isEqualTo("3");
    }

    @DisplayName("프로젝트 코드 연결 실패")
    @Test
    public void connectFail() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/connect";
        List<Long> req = List.of(0L, 1L, 2L);

        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(-1).when(projectsService)
                .connect(any(Long.class), any(Long.class), any(List.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url, 1L)
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
        assertThat(message).isEqualTo("프로젝트 코드 연결 실패");
    }

    @DisplayName("프로젝트 정보 수정 성공")
    @Test
    public void updateProjectSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/content";

        // ProjectService updateProject 대한 stub필요
        doReturn(1).when(projectsService)
                .updateProject(any(ProjectReq.class), any(Long.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));

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
        assertThat(message).isEqualTo("프로젝트 정보 수정 성공");
    }

    @DisplayName("프로젝트 정보 수정 실패")
    @Test
    public void updateProjectFail() throws Exception {
        // given
        final String url = "/api/v1/project/content";

        // ProjectService updateProject 대한 stub필요
        doReturn(0).when(projectsService)
                .updateProject(any(ProjectReq.class), any(Long.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));

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
        assertThat(message).isEqualTo("프로젝트 정보 수정 실패");
    }

    @DisplayName("프로젝트 썸네일 등록 성공")
    @Test
    public void updateProjectThumbnailSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/thumbnail";
        final Long projectId = 1L;
        MockMultipartFile thumbnail = getImage();

        // ProjectsService updateProjectThumbnail 대한 stub 필요
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(1).when(projectsService).updateProjectThumbnail(any(MultipartFile.class), any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.multipart(url, projectId)
                        .file(thumbnail)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 썸네일 등록 성공");
    }

    @DisplayName("프로젝트 썸네일 등록 실패")
    @Test
    public void updateProjectThumbnailFail() throws Exception {
        // given
        final String url = "/api/v1/project/{projectId}/thumbnail";
        final Long projectId = 1L;
        MockMultipartFile thumbnail = getImage();

        // ProjectsService updateProjectThumbnail 대한 stub 필요
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(-1).when(projectsService).updateProjectThumbnail(any(MultipartFile.class), any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.multipart(url, projectId)
                        .file(thumbnail)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 썸네일 등록 실패");
    }

    @DisplayName("프로젝트 정보 등록 성공 - n번 연속 클릭")
    @Test
    public void insertProjectSucceedN() throws Exception {
        // given
        final String url = "/api/v1/project/content";

        // ProjectService insertProject 대한 stub필요
        doReturn(1L).when(projectsService)
                .insertProject(any(ProjectReq.class), any(Long.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(operations).when(redisTemplate).opsForValue();

        // when
        doReturn(null).when(operations).get(any(String.class));
        final ResultActions resultActions1 = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(req))
        );
        doReturn("lock").when(operations).get(any(String.class));
        final ResultActions resultActions2 = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(req))
        );
        final ResultActions resultActions3 = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(req))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions1.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("프로젝트 정보 등록 성공");

        MvcResult mvcResult2 = resultActions2.andExpect(status().isTooManyRequests()).andReturn();
        responseBody = mvcResult2.getResponse().getContentAsString(StandardCharsets.UTF_8);
        jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("요청이 너무 많습니다");

        MvcResult mvcResult3 = resultActions2.andExpect(status().isTooManyRequests()).andReturn();
        responseBody = mvcResult3.getResponse().getContentAsString(StandardCharsets.UTF_8);
        jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("요청이 너무 많습니다");
    }

    @DisplayName("프로젝트 정보 등록 성공")
    @Test
    public void insertProjectSucceed() throws Exception {
        // given
        final String url = "/api/v1/project/content";

        // ProjectService insertProject 대한 stub필요
        doReturn(1L).when(projectsService)
                .insertProject(any(ProjectReq.class), any(Long.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(operations).when(redisTemplate).opsForValue();
        doReturn(null).when(operations).get(any(String.class));

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
        assertThat(message).isEqualTo("프로젝트 정보 등록 성공");
    }

    @DisplayName("프로젝트 정보 등록 실패")
    @Test
    public void insertProjectFail() throws Exception {
        // given
        final String url = "/api/v1/project/content";

        // ProjectService insertProject 대한 stub필요
        doReturn(-1L).when(projectsService)
                .insertProject(any(ProjectReq.class), any(Long.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));
        doReturn(operations).when(redisTemplate).opsForValue();
        doReturn(null).when(operations).get(any(String.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
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
        assertThat(message).isEqualTo("프로젝트 정보 등록 실패");
    }

}

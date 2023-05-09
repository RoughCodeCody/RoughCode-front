package com.cody.roughcode.code.controller;

import com.cody.roughcode.code.dto.req.CodeFavoriteReq;
import com.cody.roughcode.code.dto.req.CodeReq;
import com.cody.roughcode.code.dto.res.CodeDetailRes;
import com.cody.roughcode.code.dto.res.CodeInfoRes;
import com.cody.roughcode.code.dto.res.CodeTagsRes;
import com.cody.roughcode.code.dto.res.ReviewInfoRes;
import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.service.CodesService;
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
import java.util.ArrayList;
import java.util.List;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
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

    Users users = Users.builder()
            .usersId(1L)
            .email("cody306@gmail.com")
            .name("코디")
            .roles(List.of(String.valueOf(ROLE_USER)))
            .build();

    CodeReq req2 = CodeReq.builder()
            .title("개발새발 코드2")
            .selectedTagsId(List.of(1L, 2L))
            .githubUrl("https://api.github.com/repos/cody/hello-world/contents/src/main.py")
            .content("시간초과 뜹니다!!!!")
            .projectId(3L)
            .build();

    private List<CodeTagsRes> tagsResInit() {
        List<String> list = List.of("Javascript", "Python", "Java", "C++");
        List<CodeTagsRes> tagsList = new ArrayList<>();
        for (long i = 1L; i <= 3L; i++) {
            tagsList.add(CodeTagsRes.builder()
                    .tagId(i)
                    .name(list.get((int)i-1))
                    .cnt(0)
                    .build());
        }

        return tagsList;
    }

    @DisplayName("코드 목록 조회 성공")
    @Test
    public void getCodeListSucceed() throws Exception {
        // given
        final String url = "/api/v1/code";

        int page = 10;

        final Codes code = Codes.builder()
                .codesId(1L)
                .num(1L)
                .version(1)
                .title("title")
                .codeWriter(users)
                .build();

        List<CodeInfoRes> codeInfoRes = List.of(
                CodeInfoRes.builder()
                        .codeId(code.getCodesId())
                        .version(code.getVersion())
                        .title(code.getTitle())
                        .date(code.getModifiedDate())
                        .likeCnt(code.getLikeCnt())
                        .reviewCnt(code.getReviewCnt())
                        .tags(List.of("java", "javascript"))
                        .userName(users.getName())
                        .liked(true)
                        .build()
        );
        doReturn(codeInfoRes).when(codesService)
                .getCodeList(any(String.class), any(org.springframework.data.domain.PageRequest.class), any(String.class), any(String.class), any(Long.class));

        String keyword = "title";
        String tagIds = "2";
        int closed = 0;

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .param("sort", "modifiedDate")
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
        assertThat(message).isEqualTo("코드 목록 조회 성공");
    }

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

    @DisplayName("코드 상세 조회 성공")
    @Test
    public void getCodeSucceed() throws Exception {
        // given
        final String url = "/api/v1/code/{codeId}";

        doReturn(new CodeDetailRes()).when(codesService).getCode(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url, 1L)
        );

        // then
        // HTTP Status가 OK 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 상세 조회 성공");
    }

    @DisplayName("코드 상세 조회 실패")
    @Test
    public void getCodeFail() throws Exception {
        // given
        final String url = "/api/v1/code/{codeId}";

        doReturn(null).when(codesService).getCode(any(Long.class), any(Long.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url, 1L)
        );

        // then
        // HTTP Status가 NotFound 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 상세 조회 실패");
    }

    @DisplayName("코드 정보 수정 성공")
    @Test
    public void updateCodeSucceed() throws Exception {
        // given
        final String url = "/api/v1/code/{codeId}";

        // CodeService updateCode 대한 stub 필요
        doReturn(1).when(codesService)
                .updateCode(any(CodeReq.class), any(Long.class), any(Long.class));
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
        assertThat(message).isEqualTo("코드 정보 수정 성공");
    }

    @DisplayName("코드 정보 수정 실패")
    @Test
    public void updateCodeFail() throws Exception {
        // given
        final String url = "/api/v1/code/{codeId}";

        // CodeService updateCode 대한 stub 필요
        doReturn(0).when(codesService)
                .updateCode(any(CodeReq.class), any(Long.class), any(Long.class));
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
        assertThat(message).isEqualTo("코드 정보 수정 실패");
    }

    @DisplayName("코드 정보 삭제 성공")
    @Test
    public void deleteCodeSucceed() throws Exception {
        // given
        final String url = "/api/v1/code/{codeId}";

        // CodeService deleteCode 대한 stub 필요
        doReturn(1).when(codesService)
                .deleteCode(any(Long.class), any(Long.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));

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
        assertThat(message).isEqualTo("코드 정보 삭제 성공");
    }

    @DisplayName("코드 정보 삭제 실패")
    @Test
    public void deleteCodeFail() throws Exception {
        // given
        final String url = "/api/v1/code/{codeId}";

        // CodeService deleteCode 대한 stub 필요
        doReturn(0).when(codesService)
                .deleteCode(any(Long.class), any(Long.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
        );

        // then
        // HTTP Status가 NotFound인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 정보 삭제 실패");
    }

    @DisplayName("코드 좋아요 등록 또는 취소 성공")
    @Test
    public void likeCodeSucceed() throws Exception {
        // given
        final String url = "/api/v1/code/{codeId}/like";

        // CodeService likeCode 대한 stub 필요
        doReturn(3).when(codesService)
                .likeCode(any(Long.class), any(Long.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));

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
        assertThat(message).isEqualTo("코드 좋아요 등록 또는 취소 성공");
    }

    @DisplayName("코드 좋아요 등록 또는 취소 실패")
    @Test
    public void likeCodeFail() throws Exception {
        // given
        final String url = "/api/v1/code/{codeId}/like";

        // CodeService likeCode 대한 stub 필요
        doReturn(-1).when(codesService)
                .likeCode(any(Long.class), any(Long.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));

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
        assertThat(message).isEqualTo("코드 좋아요 등록 또는 취소 실패");
    }

    @DisplayName("코드 즐겨찾기 등록 또는 취소 성공")
    @Test
    public void favoriteCodeSucceed() throws Exception {
        // given
        CodeFavoriteReq codeFavoriteReq = CodeFavoriteReq.builder()
                .content("오.. 효율적인 코드")
                .build();

        final String url = "/api/v1/code/{codeId}/favorite";

        // CodeService favoriteCode 대한 stub 필요
        doReturn(3).when(codesService)
                .favoriteCode(any(Long.class), any(String.class), any(Long.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(codeFavoriteReq))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 즐겨찾기 등록 또는 취소 성공");
    }

    @DisplayName("코드 즐겨찾기 등록 또는 취소 실패")
    @Test
    public void favoriteCodeFail() throws Exception {
        // given
        CodeFavoriteReq codeFavoriteReq = CodeFavoriteReq.builder()
                .content("오.. 효율적인 코드")
                .build();

        final String url = "/api/v1/code/{codeId}/favorite";

        // CodeService favoriteCode 대한 stub 필요
        doReturn(-1).when(codesService)
                .favoriteCode(any(Long.class), any(String.class), any(Long.class));
        doReturn(1L).when(jwtTokenProvider).getId(any(String.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url, 1L)
                        .cookie(new Cookie(JwtProperties.ACCESS_TOKEN, accessToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(codeFavoriteReq))
        );

        // then
        // HTTP Status가 OK인지 확인
        MvcResult mvcResult = resultActions.andExpect(status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        assertThat(message).isEqualTo("코드 즐겨찾기 등록 또는 취소 실패");
    }

    @DisplayName("tag 목록 검색 성공")
    @Test
    public void searchTags() throws Exception {
        // given
        final String url = "/api/v1/code/tag";

        List<CodeTagsRes> tagList = tagsResInit();
        doReturn(tagList).when(codesService).searchTags("");

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
        assertThat(message).isEqualTo("코드 태그 목록 조회 성공");
    }

    @DisplayName("tag 목록 검색 실패")
    @Test
    public void searchTagsFail() throws Exception {
        // given
        final String url = "/api/v1/code/tag";

        doThrow(new NullPointerException()).when(codesService).searchTags("");

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
        assertThat(message).isEqualTo("코드 태그 목록 조회 실패");
    }

    @DisplayName("코드 리뷰 목록 조회 성공")
    @Test
    public void getReviewListSucceed() throws Exception {
        // given
        final String url = "/api/v1/code/{codeId}/review";

        ReviewInfoRes reviewInfoRes = ReviewInfoRes.builder()
                .reviewId(1L)
                .userName("user")
                .userId(1L)
                .codeContent("codeContent")
                .content("content")
                .selected(true)
                .build();

        doReturn(List.of(reviewInfoRes)).when(codesService)
                .getReviewList(any(Long.class), any(Long.class));

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
        assertThat(message).isEqualTo("코드 리뷰 목록 조회 성공");
    }

    @DisplayName("코드 리뷰 목록 조회 실패")
    @Test
    public void getFeedbackListFail() throws Exception {
        // given
        final String url = "/api/v1/code/{codeId}/review";

        doReturn(null).when(codesService)
                .getReviewList(any(Long.class), any(Long.class));

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
        assertThat(message).isEqualTo("코드 리뷰 목록 조회 실패");
    }
}
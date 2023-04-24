package com.cody.roughcode.project.service;

import com.cody.roughcode.code.repository.CodesRepostiory;
import com.cody.roughcode.exception.DeletionFailException;
import com.cody.roughcode.exception.NotMatchException;
import com.cody.roughcode.exception.NotNewestVersionException;
import com.cody.roughcode.exception.UpdateFailedException;
import com.cody.roughcode.project.dto.req.ProjectReq;
import com.cody.roughcode.project.entity.*;
import com.cody.roughcode.project.repository.*;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class) // 가짜 객체 주입을 사용
public class ProjectServiceTest {
    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }

    @InjectMocks
    private ProjectsServiceImpl projectsService;

    @Mock
    private ProjectsRepository projectsRepository;
    @Mock
    private ProjectsInfoRepository projectsInfoRepository;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private CodesRepostiory codesRepostiory;
    @Mock
    private ProjectTagsRepository projectTagsRepository;
    @Mock
    private ProjectSelectedTagsRepository projectSelectedTagsRepository;
    @Mock
    private S3FileServiceImpl s3FileService;
    @Mock
    private FeedbacksRepository feedbacksRepository;
    @Mock
    private SelectedFeedbacksRepository selectedFeedbacksRepository;

    final Users users = Users.builder()
            .usersId(1L)
            .email("kosy1782@gmail.com")
            .name("고수")
            .roles(List.of(String.valueOf(ROLE_USER)))
            .build();

    final Users users2 = Users.builder()
            .usersId(2L)
            .email("kosy1782@gmail.com")
            .name("고수")
            .roles(List.of(String.valueOf(ROLE_USER)))
            .build();


    final Projects project = Projects.builder()
            .projectsId(1L)
            .num(1L)
            .version(1)
            .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
            .introduction("intro")
            .title("title")
            .projectWriter(users)
            .projectsCodes(new ArrayList<>())
            .build();

    private static MockMultipartFile getThumbnail() throws IOException {
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

    private List<ProjectTags> tagsInit() {
        List<ProjectTags> tagsList = new ArrayList<>();
        for (long i = 1L; i <= 3L; i++) {
            tagsList.add(ProjectTags.builder()
                    .tagsId(i)
                    .name("tag1")
                    .build());
        }

        return tagsList;
    }

    private List<Feedbacks> feedbacksInit(Projects project) {
        List<Feedbacks> feedbacksList = new ArrayList<>();
        for (long i = 1L; i <= 3L; i++) {
            feedbacksList.add(Feedbacks.builder()
                    .projects(project)
                    .feedbacksId(i)
                    .content("content")
                    .users(null)
                    .build());
        }

        return feedbacksList;
    }

    @DisplayName("프로젝트 수정 성공")
    @Test
    void updateProjectSucceed() throws Exception {
        // given
        List<ProjectTags> tagsList = tagsInit();
        Projects project = Projects.builder()
                .num(1L)
                .version(1)
                .img("imgUrl")
                .introduction("introduction")
                .title("title")
                .projectWriter(users)
                .projectsCodes(null)
                .likeCnt(1)
                .selectedTags(new ArrayList<>())
                .build();
        ProjectsInfo info = ProjectsInfo.builder()
                .url("www.google.com")
                .notice("notice")
                .build();
        List<Feedbacks> feedbacksList = feedbacksInit(project);

        ProjectReq req = ProjectReq.builder()
                .projectId(1L)
                .title("title2")
                .url("https://www.google.com")
                .introduction("introduction2")
                .selectedTagsId(List.of(1L, 2L))
                .content("content2")
                .notice("notice2")
                .selectedFeedbacksId(List.of(1L, 2L))
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(project).when(projectsRepository).findLatestProject(any(Long.class), any(Long.class));
        doReturn(info).when(projectsInfoRepository).findByProjects(any(Projects.class));
        doReturn(tagsList.get(0)).when(projectTagsRepository).findByTagsId(any(Long.class));
        doReturn(feedbacksList.get(0)).when(feedbacksRepository).findFeedbacksByFeedbacksId(any(Long.class));

        // when
        int success = projectsService.updateProject(req, 1L);

        // then
        assertThat(success).isEqualTo(1);
    }

    // 일치하는 프로젝트 없음
    @DisplayName("프로젝트 수정 실패 - 일치하는 프로젝트 없음")
    @Test
    void updateProjectFailNoProject() throws Exception {
        // given
        ProjectReq req = ProjectReq.builder()
                .projectId(1L)
                .title("title2")
                .url("https://www.google.com")
                .introduction("introduction2")
                .selectedTagsId(List.of(1L, 2L))
                .content("content2")
                .notice("notice2")
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(projectsRepository).findByProjectsId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.updateProject(req, 1L)
        );

        assertEquals("일치하는 프로젝트가 존재하지 않습니다", exception.getMessage());
    }

    // 최신 버전의 프로젝트가 아님
    @DisplayName("프로젝트 수정 실패 - 최신 버전의 프로젝트가 아님")
    @Test
    void updateProjectFailNotNewest() throws Exception {
        // given
        Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("imgUrl")
                .introduction("introduction")
                .title("title")
                .projectWriter(users)
                .projectsCodes(null)
                .likeCnt(1)
                .selectedTags(new ArrayList<>())
                .build();
        Projects project2 = Projects.builder()
                .projectsId(2L)
                .num(2L)
                .version(1)
                .img("imgUrl")
                .introduction("introduction")
                .title("title")
                .projectWriter(users)
                .projectsCodes(null)
                .likeCnt(1)
                .selectedTags(new ArrayList<>())
                .build();

        ProjectReq req = ProjectReq.builder()
                .projectId(1L)
                .title("title2")
                .url("https://www.google.com")
                .introduction("introduction2")
                .selectedTagsId(List.of(1L, 2L))
                .content("content2")
                .notice("notice2")
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(project2).when(projectsRepository).findLatestProject(any(Long.class), any(Long.class));

        // when & then
        NotNewestVersionException exception = assertThrows(
                NotNewestVersionException.class, () -> projectsService.updateProject(req, 1L)
        );

        assertEquals("최신 버전이 아닙니다", exception.getMessage());
    }

    // 일치하는 프로젝트 정보가 없음
    @DisplayName("프로젝트 수정 실패 - 일치하는 프로젝트 정보가 없음")
    @Test
    void updateProjectFailNoProjectInfo() throws Exception {
        // given
        Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("imgUrl")
                .introduction("introduction")
                .title("title")
                .projectWriter(users)
                .projectsCodes(null)
                .likeCnt(1)
                .selectedTags(new ArrayList<>())
                .build();

        ProjectReq req = ProjectReq.builder()
                .projectId(1L)
                .title("title2")
                .url("https://www.google.com")
                .introduction("introduction2")
                .selectedTagsId(List.of(1L, 2L))
                .content("content2")
                .notice("notice2")
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(project).when(projectsRepository).findLatestProject(any(Long.class), any(Long.class));
        doReturn(null).when(projectsInfoRepository).findByProjects(any(Projects.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.updateProject(req, 1L)
        );

        assertEquals("일치하는 프로젝트가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("프로젝트 썸네일 등록 성공")
    @Test
    void updateProjectThumbnailSucceed() throws IOException {
        // given
        MockMultipartFile thumbnail = getThumbnail();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(project).when(projectsRepository).findLatestProject(any(Long.class), any(Long.class));
        doReturn("imageUrl").when(s3FileService).upload(any(MultipartFile.class), any(String.class), any(String.class));

        // when
        int success = projectsService.updateProjectThumbnail(thumbnail, 1L, 1L);

        // then
        assertThat(success).isEqualTo(1L);
    }

    @DisplayName("프로젝트 썸네일 등록 실패 - 존재하지 않는 유저 아이디")
    @Test
    void updateProjectThumbnailFailNoUser() throws IOException {
        // given
        MockMultipartFile thumbnail = getThumbnail();
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.updateProjectThumbnail(thumbnail, 1L, 1L)
        );

        assertEquals("일치하는 유저가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("프로젝트 썸네일 등록 실패 - 썸네일이 등록되어있지 않음")
    @Test
    void updateProjectThumbnailFailNoThumbnail() throws IOException {
        // given
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.updateProjectThumbnail(null, 1L, 1L)
        );

        assertEquals("썸네일이 등록되어있지 않습니다", exception.getMessage());
    }

    @DisplayName("프로젝트 썸네일 등록 실패 - 일치하는 프로젝트가 없음")
    @Test
    void updateProjectThumbnailFailNoProject() throws IOException {
        MockMultipartFile thumbnail = getThumbnail();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(projectsRepository).findByProjectsId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.updateProjectThumbnail(thumbnail, 1L, 1L)
        );

        assertEquals("일치하는 프로젝트가 없습니다", exception.getMessage());
    }

    @DisplayName("프로젝트 썸네일 등록 실패 - 등록하려는 유저와 프로젝트의 유저가 일치하지 않음")
    @Test
    void updateProjectThumbnailFailUserDiffer() throws IOException {

        MockMultipartFile thumbnail = getThumbnail();
        Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .projectsCodes(new ArrayList<>())
                .build();

        doReturn(users2).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));

        // when & then
        NotMatchException exception = assertThrows(
                NotMatchException.class, () -> projectsService.updateProjectThumbnail(thumbnail, 1L, 1L)
        );

        assertEquals("접근 권한이 없습니다", exception.getMessage());
    }

    @DisplayName("프로젝트 등록 성공 - 새 프로젝트")
    @Test
    void insertProjectSucceed() {
        // given
        List<ProjectTags> tagsList = tagsInit();
        ProjectReq req = ProjectReq.builder()
                .projectId((long) -1)
                .title("title")
                .url("https://www.google.com")
                .introduction("introduction")
                .selectedTagsId(List.of(1L))
                .content("content")
                .notice("notice")
                .build();

        Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                .introduction(req.getIntroduction())
                .title(req.getTitle())
                .projectWriter(users)
                .projectsCodes(new ArrayList<>())
                .build();
        ProjectsInfo info = ProjectsInfo.builder()
                .url(req.getUrl())
                .notice(req.getNotice())
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(users).when(usersRepository).save(any(Users.class));
        doReturn(project).when(projectsRepository).save(any(Projects.class));
        doReturn(tagsList.get(0)).when(projectTagsRepository).findByTagsId(any(Long.class));
        doReturn(ProjectSelectedTags.builder()
                .tags(tagsList.get(0))
                .projects(project)
                .build())
                .when(projectSelectedTagsRepository)
                .save(any(ProjectSelectedTags.class));
        doReturn(tagsList.get(0)).when(projectTagsRepository).save(any(ProjectTags.class));
        doReturn(info).when(projectsInfoRepository).save(any(ProjectsInfo.class));

        // when
        Long success = projectsService.insertProject(req, 1L);

        // then
        assertThat(success).isEqualTo(1L);
    }

    @DisplayName("프로젝트 등록 성공 - 기존 프로젝트 업데이트")
    @Test
    void insertProjectSucceedVersionUp() {
        // given
        List<ProjectTags> tagsList = tagsInit();
        ProjectReq req = ProjectReq.builder()
                .projectId((long) 1)
                .title("title")
                .url("https://www.google.com")
                .introduction("introduction")
                .selectedTagsId(List.of(1L))
                .content("content")
                .notice("notice")
                .build();

        Projects project = Projects.builder()
                .projectsId(2L)
                .num(1L)
                .version(2)
                .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                .introduction(req.getIntroduction())
                .title(req.getTitle())
                .projectWriter(users)
                .projectsCodes(new ArrayList<>())
                .build();
        ProjectsInfo info = ProjectsInfo.builder()
                .url(req.getUrl())
                .notice(req.getNotice())
                .build();

        Projects original = Projects.builder()
                .num(1L)
                .version(1)
                .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                .introduction(req.getIntroduction())
                .title(req.getTitle())
                .projectWriter(users)
                .projectsCodes(new ArrayList<>())
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(original).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(original).when(projectsRepository).findLatestProject(any(Long.class), any(Long.class));
        doReturn(project).when(projectsRepository).save(any(Projects.class));
        doReturn(tagsList.get(0)).when(projectTagsRepository).findByTagsId(any(Long.class));
        doReturn(ProjectSelectedTags.builder()
                .tags(tagsList.get(0))
                .projects(project)
                .build())
                .when(projectSelectedTagsRepository)
                .save(any(ProjectSelectedTags.class));
        doReturn(tagsList.get(0)).when(projectTagsRepository).save(any(ProjectTags.class));
        doReturn(info).when(projectsInfoRepository).save(any(ProjectsInfo.class));

        // when
        Long success = projectsService.insertProject(req, 1L);

        // then
        assertThat(success).isEqualTo(2);
    }

    @DisplayName("프로젝트 등록 실패 - 존재하지 않는 유저 아이디")
    @Test
    void insertProjectFailNoUser() {
        // given
        ProjectReq req = ProjectReq.builder()
                .projectId(1L)
                .title("title")
                .url("https://www.google.com")
                .introduction("introduction")
                .selectedTagsId(List.of(1L))
                .content("content")
                .notice("notice")
                .build();

        // when & then
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.insertProject(req, 1L)
        );

        assertEquals("일치하는 유저가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("프로젝트 등록 실패 - 존재하지 않는 project id")
    @Test
    void insertProjectFailNoProject() {
        // given
        ProjectReq req = ProjectReq.builder()
                .projectId(1L)
                .title("title")
                .url("https://www.google.com")
                .introduction("introduction")
                .selectedTagsId(List.of(1L))
                .content("content")
                .notice("notice")
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(projectsRepository).findByProjectsId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.insertProject(req, 1L)
        );

        assertEquals("일치하는 프로젝트가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("프로젝트 등록 실패 - project 작성한 user랑 version up 하려는 user가 다름")
    @Test
    void insertProjectFailUserDiffer() {
        // given
        ProjectReq req = ProjectReq.builder()
                .projectId(1L)
                .title("title")
                .url("https://www.google.com")
                .introduction("introduction")
                .selectedTagsId(List.of(1L))
                .content("content")
                .notice("notice")
                .build();

        Projects original = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                .introduction(req.getIntroduction())
                .title(req.getTitle())
                .projectWriter(users2)
                .projectsCodes(new ArrayList<>())
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(original).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(original).when(projectsRepository).findLatestProject(any(Long.class), any(Long.class));

        // when & then
        NotMatchException exception = assertThrows(
                NotMatchException.class, () -> projectsService.insertProject(req, 1L)
        );

        assertEquals("접근 권한이 없습니다", exception.getMessage());
    }

}

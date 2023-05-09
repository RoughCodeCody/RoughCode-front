package com.cody.roughcode.project.service;

import com.cody.roughcode.alarm.service.AlarmServiceImpl;
import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.email.service.EmailServiceImpl;
import com.cody.roughcode.exception.NotMatchException;
import com.cody.roughcode.exception.NotNewestVersionException;
import com.cody.roughcode.project.dto.req.FeedbackInsertReq;
import com.cody.roughcode.project.dto.req.FeedbackUpdateReq;
import com.cody.roughcode.project.dto.res.FeedbackInfoRes;
import com.cody.roughcode.project.dto.res.ProjectInfoRes;
import com.cody.roughcode.code.repository.CodesRepository;
import com.cody.roughcode.project.dto.req.ProjectReq;
import com.cody.roughcode.project.dto.res.ProjectDetailRes;
import com.cody.roughcode.project.dto.res.ProjectTagsRes;
import com.cody.roughcode.project.entity.*;
import com.cody.roughcode.project.repository.*;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

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
    private CodesRepository codesRepository;
    @Mock
    private ProjectTagsRepository projectTagsRepository;
    @Mock
    private ProjectSelectedTagsRepository projectSelectedTagsRepository;
    @Mock
    private S3FileServiceImpl s3FileService;
    @Mock
    private AlarmServiceImpl alarmService;
    @Mock
    private EmailServiceImpl emailService;
    @Mock
    private FeedbacksRepository feedbacksRepository;
    @Mock
    private SelectedFeedbacksRepository selectedFeedbacksRepository;
    @Mock
    private ProjectFavoritesRepository projectFavoritesRepository;
    @Mock
    private ProjectLikesRepository projectLikesRepository;
    @Mock
    private FeedbacksLikesRepository feedbacksLikesRepository;

    final Users users = Users.builder()
            .usersId(1L)
            .email("kosy1782@gmail.com")
            .name("kosy318")
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
            .feedbackCnt(1)
            .build();

    final ProjectsInfo info = ProjectsInfo.builder()
            .url("www.google.com")
            .notice("notice")
            .projects(project)
            .build();

    final Feedbacks feedbacks = Feedbacks.builder()
            .feedbacksId(1L)
            .projectsInfo(info)
            .content("content")
            .build();

    private static MockMultipartFile getImage() throws IOException {
        File imageFile = new File("src/test/java/com/cody/roughcode/resources/image/A306_ERD (2).png");
        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
        return new MockMultipartFile(
                "thumbnail",
                "A306_ERD (2).png",
                MediaType.IMAGE_PNG_VALUE,
                imageBytes
        );
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

    private List<ProjectSelectedTags> selectedTagsInit() {
        List<ProjectTags> tags = tagsInit();
        List<ProjectSelectedTags> tagsList = new ArrayList<>();
        for (long i = 1L; i <= 3L; i++) {
            tagsList.add(ProjectSelectedTags.builder()
                    .selectedTagsId(i)
                    .tags(tags.get((int) i - 1))
                    .projects(project)
                    .build());
        }

        return tagsList;
    }

    private List<Feedbacks> feedbacksInit() {
        List<Feedbacks> feedbacksList = new ArrayList<>();
        for (long i = 1L; i <= 3L; i++) {
            feedbacksList.add(Feedbacks.builder()
                    .projectsInfo(info)
                    .feedbacksId(i)
                    .content("content")
                    .users(null)
                    .build());
        }

        return feedbacksList;
    }

    private List<Codes> codeInit() {
        List<Codes> codeList = new ArrayList<>();
        for (long i = 1L; i <= 3L; i++) {
            codeList.add(Codes.builder()
                .codesId(1L)
                .codeWriter(users)
                .version(1)
                .num(1L)
                .build());
        }

        return codeList;
    }

    @DisplayName("프로젝트 닫기 성공")
    @Test
    void projectCloseSucceed(){
        // given
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(project).when(projectsRepository).findLatestProject(any(Long.class), any(Long.class));

        // when
        int res = projectsService.openProject(1L, 1L);

        // then
        assertThat(res).isEqualTo(1);
    }

    @DisplayName("프로젝트 열기 성공")
    @Test
    void projectOpenSucceed(){
        // given
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(project).when(projectsRepository).findLatestProject(any(Long.class), any(Long.class));

        // when
        int res = projectsService.openProject(1L, 1L);

        // then
        assertThat(res).isEqualTo(1);
    }

    @DisplayName("이미지 삭제 성공")
    @Test
    void deleteImageSucceed(){
        // given
        String imgUrl = "https://roughcode.s3.ap-northeast-2.amazonaws.com/project/content/kosy318_1_1_2023-05-01%2014%3A29%3A07";
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));

        // when
        int res = projectsService.deleteImage(imgUrl, project.getProjectsId(), users.getUsersId());

        // then
        assertThat(res).isEqualTo(1);
    }

    @DisplayName("이미지 삭제 실패 - 일치하는 유저 없음")
    @Test
    void deleteImageFailNoUser(){
        // given
        String imgUrl = "https://roughcode.s3.ap-northeast-2.amazonaws.com/project/content/kosy318_1_1_2023-05-01%2014%3A29%3A07";
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.deleteImage(imgUrl, project.getProjectsId(), users.getUsersId())
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 유저가 존재하지 않습니다");
    }

    @DisplayName("이미지 삭제 실패 - 일치하는 프로젝트 없음")
    @Test
    void deleteImageFailNoProject(){
        // given
        String imgUrl = "https://roughcode.s3.ap-northeast-2.amazonaws.com/project/content/kosy318_1_1_2023-05-01%2014%3A29%3A07";
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(projectsRepository).findByProjectsId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.deleteImage(imgUrl, project.getProjectsId(), users.getUsersId())
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 프로젝트가 존재하지 않습니다");
    }

    @DisplayName("이미지 삭제 실패 - 이미지가 프로젝트 정보와 일치하지 않음")
    @Test
    void deleteImageFail(){
        // given
        String imgUrl = "https://roughcode.s3.ap-northeast-2.amazonaws.com/project/content/kosy318_1_2_2023-05-01%2014%3A29%3A07";
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));

        // when & then
        NotMatchException exception = assertThrows(
                NotMatchException.class, () -> projectsService.deleteImage(imgUrl, project.getProjectsId(), users.getUsersId())
        );
        assertThat(exception.getMessage()).isEqualTo("접근 권한이 없습니다");
    }

    @DisplayName("이미지 등록 성공")
    @Test
    void insertImageSucceed() throws IOException {
        // given
        MockMultipartFile image = getImage();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn("imageUrl").when(s3FileService).upload(any(MultipartFile.class), any(String.class), any(String.class));

        // when
        String imageUrl = projectsService.insertImage(image, 1L, 1L);

        // then
        assertThat(imageUrl).isEqualTo("imageUrl");
    }

    @DisplayName("이미지 등록 실패 - 존재하지 않는 유저 아이디")
    @Test
    void insertImageFailNoUser() throws IOException {
        // given
        MockMultipartFile thumbnail = getImage();
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.insertImage(thumbnail, 1L, 1L)
        );

        assertEquals("일치하는 유저가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("이미지 등록 실패 - 일치하는 프로젝트가 없음")
    @Test
    void insertImageFailNoProject() throws IOException {
        MockMultipartFile image = getImage();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(projectsRepository).findByProjectsId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.insertImage(image, 1L, 1L)
        );

        assertEquals("일치하는 프로젝트가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("이미지 등록 실패 - 등록하려는 유저와 프로젝트의 유저가 일치하지 않음")
    @Test
    void insertImageFailUserDiffer() throws IOException {

        MockMultipartFile image = getImage();
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
                NotMatchException.class, () -> projectsService.insertImage(image, 1L, 1L)
        );

        assertEquals("접근 권한이 없습니다", exception.getMessage());
    }
    
    @DisplayName("피드백 좋아요 등록 성공")
    @Test
    void feedbackLikeSucceed(){
        // given
        doReturn(users).when(usersRepository).findByUsersId(1L);
        doReturn(feedbacks).when(feedbacksRepository).findByFeedbacksId(1L);
        doReturn(null).when(feedbacksLikesRepository).findByFeedbacksAndUsers(any(Feedbacks.class), any(Users.class));

        // when
        int likes = projectsService.likeProjectFeedback(feedbacks.getFeedbacksId(), users.getUsersId());

        // then
        assertThat(likes).isEqualTo(1);
    }

    @DisplayName("피드백 좋아요 취소 성공")
    @Test
    void feedbackLikeCancelSucceed(){
        // given
        FeedbacksLikes feedbacksLikes = FeedbacksLikes.builder()
                .feedbacks(feedbacks)
                .users(users)
                .build();

        doReturn(users).when(usersRepository).findByUsersId(1L);
        doReturn(feedbacks).when(feedbacksRepository).findByFeedbacksId(1L);
        doReturn(feedbacksLikes).when(feedbacksLikesRepository).findByFeedbacksAndUsers(any(Feedbacks.class), any(Users.class));

        // when
        int likes = projectsService.likeProjectFeedback(feedbacks.getFeedbacksId(), users.getUsersId());

        // then
        assertThat(likes).isEqualTo(0);
    }

    @DisplayName("피드백 좋아요 실패 - 존재하지 않는 유저")
    @Test
    void feedbackLikeFailNoUser(){
        // given
        doReturn(null).when(usersRepository).findByUsersId(1L);

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.likeProjectFeedback(feedbacks.getFeedbacksId(), users.getUsersId())
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 유저가 존재하지 않습니다");
    }

    @DisplayName("피드백 좋아요 실패 - 존재하지 않는 피드백")
    @Test
    void feedbackLikeFailNoFeedback(){
        // given
        doReturn(users).when(usersRepository).findByUsersId(1L);
        doReturn(null).when(feedbacksRepository).findByFeedbacksId(1L);

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.likeProjectFeedback(feedbacks.getFeedbacksId(), users.getUsersId())
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 피드백이 존재하지 않습니다");
    }

    @DisplayName("프로젝트 즐겨찾기 성공")
    @Test
    void projectFavoriteSucceed(){
        // given
        doReturn(users).when(usersRepository).findByUsersId(1L);
        doReturn(project).when(projectsRepository).findByProjectsId(1L);
        doReturn(info).when(projectsInfoRepository).findByProjects(any(Projects.class));
        doReturn(null).when(projectFavoritesRepository).findByProjectsAndUsers(any(Projects.class), any(Users.class));

        // when
        int likes = projectsService.favoriteProject(project.getProjectsId(), users.getUsersId());

        // then
        assertThat(likes).isEqualTo(1);
    }

    @DisplayName("프로젝트 즐겨찾기 취소 성공")
    @Test
    void projectFavoriteCancelSucceed(){
        // given
        ProjectFavorites projectFavorites = ProjectFavorites.builder()
                .projects(project)
                .users(users)
                .build();

        doReturn(users).when(usersRepository).findByUsersId(1L);
        doReturn(project).when(projectsRepository).findByProjectsId(1L);
        doReturn(info).when(projectsInfoRepository).findByProjects(any(Projects.class));
        doReturn(projectFavorites).when(projectFavoritesRepository).findByProjectsAndUsers(any(Projects.class), any(Users.class));

        // when
        int likes = projectsService.favoriteProject(project.getProjectsId(), users.getUsersId());

        // then
        assertThat(likes).isEqualTo(0);
    }

    @DisplayName("프로젝트 즐겨찾기 실패 - 존재하지 않는 유저")
    @Test
    void projectFavoriteFailNoUser(){
        // given
        doReturn(null).when(usersRepository).findByUsersId(1L);

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.favoriteProject(project.getProjectsId(), users.getUsersId())
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 유저가 존재하지 않습니다");
    }

    @DisplayName("프로젝트 즐겨찾기 실패 - 존재하지 않는 프로젝트")
    @Test
    void projectFavoriteFailNoProject(){
        // given
        doReturn(users).when(usersRepository).findByUsersId(1L);
        doReturn(null).when(projectsRepository).findByProjectsId(1L);

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.favoriteProject(project.getProjectsId(), users.getUsersId())
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 프로젝트가 존재하지 않습니다");
    }

    @DisplayName("프로젝트 좋아요 등록 성공")
    @Test
    void projectLikeSucceed(){
        // given
        doReturn(users).when(usersRepository).findByUsersId(1L);
        doReturn(project).when(projectsRepository).findByProjectsId(1L);
        doReturn(null).when(projectLikesRepository).findByProjectsAndUsers(any(Projects.class), any(Users.class));

        // when
        int likes = projectsService.likeProject(project.getProjectsId(), users.getUsersId());

        // then
        assertThat(likes).isEqualTo(1);
    }

    @DisplayName("프로젝트 좋아요 취소 성공")
    @Test
    void projectLikeCancelSucceed(){
        // given
        ProjectLikes projectLikes = ProjectLikes.builder()
                .projects(project)
                .users(users)
                .build();

        doReturn(users).when(usersRepository).findByUsersId(1L);
        doReturn(project).when(projectsRepository).findByProjectsId(1L);
        doReturn(projectLikes).when(projectLikesRepository).findByProjectsAndUsers(any(Projects.class), any(Users.class));

        // when
        int likes = projectsService.likeProject(project.getProjectsId(), users.getUsersId());

        // then
        assertThat(likes).isEqualTo(0);
    }

    @DisplayName("프로젝트 좋아요 실패 - 존재하지 않는 유저")
    @Test
    void projectLikeFailNoUser(){
        // given
        doReturn(null).when(usersRepository).findByUsersId(1L);

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.likeProject(project.getProjectsId(), users.getUsersId())
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 유저가 존재하지 않습니다");
    }

    @DisplayName("프로젝트 좋아요 실패 - 존재하지 않는 프로젝트")
    @Test
    void projectLikeFailNoProject(){
        // given
        doReturn(users).when(usersRepository).findByUsersId(1L);
        doReturn(null).when(projectsRepository).findByProjectsId(1L);

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.likeProject(project.getProjectsId(), users.getUsersId())
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 프로젝트가 존재하지 않습니다");
    }

    @DisplayName("프로젝트 닫힘 확인 성공 - 열려있음")
    @Test
    void isProjectClosedSucceedOpen() throws MessagingException, IOException {
        // given
        String url = "https://google.com";
        final Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .feedbackCnt(1)
                .build();

        final ProjectsInfo info = ProjectsInfo.builder()
                .url(url)
                .notice("notice")
                .projects(project)
                .build();

        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(info).when(projectsInfoRepository).findByProjects(any(Projects.class));

        // when
        int open = projectsService.isProjectOpen(1L);

        // then
        assertThat(open).isEqualTo(1);
    }

    @DisplayName("프로젝트 닫힘 확인 성공 - 닫혀있음")
    @Test
    void isProjectClosedSucceedClose() throws MessagingException, IOException {
        // given
        String url = "http://rough-code.com";
        final Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .feedbackCnt(1)
                .build();

        final ProjectsInfo info = ProjectsInfo.builder()
                .url(url)
                .notice("notice")
                .projects(project)
                .build();

        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(info).when(projectsInfoRepository).findByProjects(any(Projects.class));

        // when
        int open = projectsService.isProjectOpen(1L);

        // then
        assertThat(open).isEqualTo(0);
    }

    @DisplayName("프로젝트 닫힘 확인 성공 - 닫힌지 1시간 지남")
    @Test
    void isProjectClosedSucceedCloseProject() throws MessagingException, IOException {
        // given
        String url = "http://rough-code.com";
        final Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .feedbackCnt(1)
                .build();

        final ProjectsInfo info = ProjectsInfo.builder()
                .url(url)
                .notice("notice")
                .projects(project)
                .closedChecked(LocalDateTime.now().minusMinutes(70))
                .build();

        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(info).when(projectsInfoRepository).findByProjects(any(Projects.class));

        // when
        int open = projectsService.isProjectOpen(1L);

        // then
        assertThat(open).isEqualTo(-1);
    }

    @DisplayName("feedback 신고 성공")
    @Test
    void feedbackComplainSucceed(){
        // given
        Feedbacks feedback = Feedbacks.builder()
                .content("feedback")
                .selected(0)
                .users(users)
                .feedbacksId(1L)
                .projectsInfo(info)
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(feedback).when(feedbacksRepository).findByFeedbacksId(any(Long.class));

        // when
        int success = projectsService.feedbackComplain(1L, 1L);

        // then
        assertThat(success).isEqualTo(1);
    }

    @DisplayName("feedback 신고 실패 - 이미 삭제된 피드백")
    @Test
    void feedbackComplainFailAlreadyDeleted(){
        // given
        Feedbacks feedback = Feedbacks.builder()
                .content("")
                .selected(0)
                .users(users)
                .feedbacksId(1L)
                .projectsInfo(info)
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(feedback).when(feedbacksRepository).findByFeedbacksId(any(Long.class));

        // when & then
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class, () -> projectsService.feedbackComplain(1L, 1L)
        );
        assertEquals("이미 삭제된 피드백입니다", exception.getReason());
    }

    @DisplayName("feedback 신고 실패 - 이미 신고한 피드백")
    @Test
    void feedbackComplainFailAlreadyComplained(){
        // given
        Feedbacks feedback = Feedbacks.builder()
                .content("feedback")
                .selected(0)
                .users(users)
                .feedbacksId(1L)
                .projectsInfo(info)
                .complaint("1")
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(feedback).when(feedbacksRepository).findByFeedbacksId(any(Long.class));

        // when & then
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class, () -> projectsService.feedbackComplain(1L, 1L)
        );
        assertEquals("이미 신고한 피드백입니다", exception.getReason());
    }

    @DisplayName("feedback 신고 실패 - 일치하는 유저 없음")
    @Test
    void feedbackComplainFailNoUser(){
        // given
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.feedbackComplain(1L, 1L)
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 유저가 존재하지 않습니다");
    }

    @DisplayName("feedback 신고 실패 - 일치하는 피드백 없음")
    @Test
    void feedbackComplainFailNoFeedback(){
        // given
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(feedbacksRepository).findByFeedbacksId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.feedbackComplain(1L, 1L)
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 피드백이 존재하지 않습니다");
    }

    @DisplayName("tag 목록 검색 성공")
    @Test
    void searchTagsSucceed(){
        // given
        List<ProjectTags> tags = tagsInit();
        doReturn(tags).when(projectTagsRepository).findAllByNameContaining("", Sort.by(Sort.Direction.ASC, "name"));

        // when
        List<ProjectTagsRes> result = projectsService.searchTags("");

        // then
        assertThat(result.size()).isEqualTo(3);
    }

    @DisplayName("url 체크 - safe")
    @Test
    void checkProjectUrlSafe() throws IOException {
        // given
        String url = "http://www.google.com";
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));

        // when
        Boolean success = projectsService.checkProject(url, false);

        // then
        assertThat(success).isTrue();
    }

    @DisplayName("url 체크 - not safe")
    @Test
    void checkProjectUrlNotSafe() throws IOException {
        // given
        String url = "http://testsafebrowsing.appspot.com/apiv4/ANY_PLATFORM/MALWARE/URL/";
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));

        // when
        Boolean success = projectsService.checkProject(url, false);

        // then
        assertThat(success).isFalse();
    }

    @DisplayName("url 체크 - not opened")
    @Test
    void checkProjectUrlNotOpened() throws IOException {
        // given
        String url = "http://15.164.198.227:8080";
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        IOException exception = assertThrows(
                IOException.class, () -> projectsService.checkProject(url, false)
        );
        assertThat(exception.getMessage()).isEqualTo("서버 확인이 필요한 URL입니다");
    }

    @DisplayName("피드백 삭제 성공")
    @Test
    void deleteFeedbackSucceed(){
        // given
        Feedbacks feedback = Feedbacks.builder()
                .content("feedback")
                .selected(0)
                .users(users)
                .feedbacksId(1L)
                .projectsInfo(info)
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(feedback).when(feedbacksRepository).findByFeedbacksId(any(Long.class));

        // when
        int success = projectsService.deleteFeedback(1L, 1L);

        // then
        assertThat(success).isEqualTo(1);
    }

    @DisplayName("피드백 삭제 실패 - 선택된 피드백입니다")
    @Test
    void deleteFeedbackFail(){
        // given
        Feedbacks feedback = Feedbacks.builder()
                .content("feedback")
                .selected(1)
                .users(users)
                .feedbacksId(1L)
                .projectsInfo(info)
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(feedback).when(feedbacksRepository).findByFeedbacksId(any(Long.class));

        // when & then
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class, () -> projectsService.deleteFeedback(1L, 1L)
        );
        assertEquals("채택된 피드백은 삭제할 수 없습니다", exception.getReason());
    }

    @DisplayName("피드백 삭제 실패 - 존재하지 않는 피드백입니다")
    @Test
    void deleteFeedbackFailNoFeedback(){
        // given
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(feedbacksRepository).findByFeedbacksId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.deleteFeedback(1L, 1L)
        );
        assertEquals("일치하는 피드백이 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("피드백 삭제 실패 - 피드백 작성자 != 로그인 유저")
    @Test
    void deleteFeedbackFailNotMatch(){
        // given
        Feedbacks feedback = Feedbacks.builder()
                .content("feedback")
                .selected(1)
                .users(users2)
                .feedbacksId(1L)
                .projectsInfo(info)
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(feedback).when(feedbacksRepository).findByFeedbacksId(any(Long.class));

        // when & then
        NotMatchException exception = assertThrows(
                NotMatchException.class, () -> projectsService.deleteFeedback(1L, 1L)
        );
        assertEquals("접근 권한이 없습니다", exception.getMessage());
    }


    @DisplayName("피드백 목록 조회 성공")
    @Test
    void getFeedbackListSucceed(){
        // given
        Feedbacks feedbacks = Feedbacks.builder()
                .feedbacksId(1L)
                .content("feedback")
                .selected(0)
                .users(users)
                .build();

        final ProjectsInfo info = ProjectsInfo.builder()
                .url("www.google.com")
                .notice("notice")
                .feedbacks(List.of(feedbacks))
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(List.of(project)).when(projectsRepository).findByNumAndProjectWriterOrderByVersionDesc(any(Long.class), any(Users.class));
        doReturn(info).when(projectsInfoRepository).findByProjects(any(Projects.class));

        // when
        List<FeedbackInfoRes> success = projectsService.getFeedbackList(1L, 1L);

        // then
        assertThat(success.size()).isEqualTo(1);
    }

    @DisplayName("피드백 수정 성공")
    @Test
    void updateFeedbackSucceed(){
        // given
        FeedbackUpdateReq req = FeedbackUpdateReq.builder()
                .feedbackId(1L)
                .content("수정된feedback")
                .build();
        Feedbacks feedbacks = Feedbacks.builder()
                .content("feedback")
                .selected(0)
                .users(users)
                .build();
        Feedbacks updated = Feedbacks.builder()
                .content("수정된feedback")
                .selected(0)
                .users(users)
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(feedbacks).when(feedbacksRepository).findByFeedbacksId(any(Long.class));
        doReturn(updated).when(feedbacksRepository).save(any(Feedbacks.class));

        // when
        Boolean success = projectsService.updateFeedback(req, 1L);

        // then
        assertThat(success).isTrue();
    }

    @DisplayName("피드백 수정 실패 - 로그인 안함")
    @Test
    void updateFeedbackFailNoUser(){
        // given
        FeedbackUpdateReq req = FeedbackUpdateReq.builder()
                .feedbackId(1L)
                .content("수정된feedback")
                .build();

        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
            NullPointerException.class, () -> projectsService.updateFeedback(req, 1L)
        );
        assertEquals("일치하는 유저가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("피드백 수정 실패 - 익명의 피드백임")
    @Test
    void updateFeedbackFailAnonymousUser(){
        // given
        FeedbackUpdateReq req = FeedbackUpdateReq.builder()
                .feedbackId(1L)
                .content("수정된feedback")
                .build();
        Feedbacks feedbacks = Feedbacks.builder()
                .content("feedback")
                .selected(0)
                .users(null)
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(feedbacks).when(feedbacksRepository).findByFeedbacksId(any(Long.class));

        // when & then
        NotMatchException exception = assertThrows(
                NotMatchException.class, () -> projectsService.updateFeedback(req, 1L)
        );
        assertEquals("접근 권한이 없습니다", exception.getMessage());
    }

    @DisplayName("피드백 수정 실패 - 일치하는 유저가 아님")
    @Test
    void updateFeedbackFailUserNotMatch(){
        // given
        FeedbackUpdateReq req = FeedbackUpdateReq.builder()
                .feedbackId(1L)
                .content("수정된feedback")
                .build();
        Feedbacks feedbacks = Feedbacks.builder()
                .content("feedback")
                .selected(0)
                .users(users2)
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(feedbacks).when(feedbacksRepository).findByFeedbacksId(any(Long.class));

        // when & then
        NotMatchException exception = assertThrows(
                NotMatchException.class, () -> projectsService.updateFeedback(req, 1L)
        );
        assertEquals("접근 권한이 없습니다", exception.getMessage());
    }

    @DisplayName("피드백 수정 실패 - 존재하지 않는 피드백")
    @Test
    void updateFeedbackFailNoFeedback(){
        // given
        FeedbackUpdateReq req = FeedbackUpdateReq.builder()
                .feedbackId(1L)
                .content("수정된feedback")
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(feedbacksRepository).findByFeedbacksId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.updateFeedback(req, 1L)
        );
        assertEquals("일치하는 피드백이 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("피드백 수정 실패 - 이미 선택돼서 수정 불가")
    @Test
    void updateFeedbackFailAlreadySelected(){
        // given
        FeedbackUpdateReq req = FeedbackUpdateReq.builder()
                .feedbackId(1L)
                .content("수정된feedback")
                .build();
        Feedbacks feedbacks = Feedbacks.builder()
                .content("feedback")
                .selected(1)
                .users(users)
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(feedbacks).when(feedbacksRepository).findByFeedbacksId(any(Long.class));

        // when & then
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class, () -> projectsService.updateFeedback(req, 1L)
        );
        assertEquals("채택된 피드백은 수정할 수 없습니다", exception.getReason());
    }

    @DisplayName("피드백 등록 성공 - 로그인")
    @Test
    void insertFeedbackSucceedWithLogin() throws MessagingException {
        // given
        FeedbackInsertReq req = FeedbackInsertReq.builder()
                .content("개발새발 최고")
                .projectId(1L)
                .build();
        Feedbacks feedbacks = Feedbacks.builder()
                .projectsInfo(info)
                .content(req.getContent())
                .users(users)
                .build();

        doReturn(users).when(usersRepository).findByUsersId(1L);
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(info).when(projectsInfoRepository).findByProjects(any(Projects.class));
        doReturn(feedbacks).when(feedbacksRepository).save(any(Feedbacks.class));

        // when
        int success = projectsService.insertFeedback(req, 1L);

        // then
        assertThat(success).isEqualTo(1);
    }

    @DisplayName("피드백 등록 성공 - 로그인x")
    @Test
    void insertFeedbackSucceedWithoutLogin() throws MessagingException {
        // given
        FeedbackInsertReq req = FeedbackInsertReq.builder()
                .content("개발새발 최고")
                .projectId(1L)
                .build();
        Feedbacks feedbacks = Feedbacks.builder()
                .projectsInfo(info)
                .content(req.getContent())
                .users(null)
                .build();

        doReturn(null).when(usersRepository).findByUsersId(1L);
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(info).when(projectsInfoRepository).findByProjects(any(Projects.class));
        doReturn(feedbacks).when(feedbacksRepository).save(any(Feedbacks.class));

        // when
        int success = projectsService.insertFeedback(req, 1L);

        // then
        assertThat(success).isEqualTo(1);
    }

    @DisplayName("피드백 등록 실패 - 프로젝트 없음")
    @Test
    void insertFeedbackFailNoProject() {
        // given
        FeedbackInsertReq req = FeedbackInsertReq.builder()
                .content("개발새발 최고")
                .projectId(1L)
                .build();

        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(projectsRepository).findByProjectsId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.insertFeedback(req, any(Long.class))
        );

        assertEquals("일치하는 프로젝트가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("프로젝트 상세 조회 성공")
    @Test
    void getProjectSucceed() {
        // given
        Long projectId = 1L;
        Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                .introduction("introduction")
                .title("title")
                .projectWriter(users)
                .projectsCodes(new ArrayList<>())
                .build();
        Projects project2 = Projects.builder()
                .projectsId(2L)
                .num(1L)
                .version(2)
                .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                .introduction("introduction")
                .title("title2")
                .projectWriter(users)
                .projectsCodes(new ArrayList<>())
                .build();
        Codes code = Codes.builder()
                .codesId(2L)
                .version(1)
                .codeWriter(users)
                .title("title")
                .projects(project)
                .num(1L)
                .build();
        project.setCodes(List.of(code));
        ProjectsInfo info = ProjectsInfo.builder()
                .url("url")
                .notice("notice")
                .build();

        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(info).when(projectsInfoRepository).findByProjects(any(Projects.class));
        doReturn(List.of(project, project2)).when(projectsRepository).findByNumAndProjectWriterOrderByVersionDesc(any(Long.class), any(Users.class));

        // when
        ProjectDetailRes success = projectsService.getProject(projectId, 0L);

        // then
        System.out.println(success.toString());
        assertThat(success.getProjectId()).isEqualTo(1L);
        assertThat(success.getVersions().size()).isEqualTo(2);
    }


    @DisplayName("프로젝트 목록 조회 성공 - tag x, closed 포함, 최신순")
    @Test
    void getProjectListWithClosedWithoutTagsNewest(){
        // given
        List<ProjectTags> tagsList = tagsInit();
        List<ProjectSelectedTags> selectedTagsList = List.of(ProjectSelectedTags.builder()
                .selectedTagsId(2L)
                .tags(tagsList.get(1))
                .projects(project)
                .build());
        List<Projects> projectsList = List.of(
                Projects.builder()
                        .projectsId(1L)
                        .num(1L)
                        .version(1)
                        .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                        .introduction("intro")
                        .title("title")
                        .projectWriter(users)
                        .selectedTags(selectedTagsList)
                        .build()
        );

        String sort = "modifiedDate";
        int page = 0;

        List<ProjectInfoRes> projectInfoRes = List.of(
                ProjectInfoRes.builder()
                        .date(projectsList.get(0).getModifiedDate())
                        .img(projectsList.get(0).getImg())
                        .projectId(projectsList.get(0).getProjectsId())
                        .feedbackCnt(projectsList.get(0).getFeedbackCnt())
                        .introduction(projectsList.get(0).getIntroduction())
                        .likeCnt(projectsList.get(0).getLikeCnt())
                        .tags(List.of(tagsList.get(1).getName()))
                        .title(projectsList.get(0).getTitle())
                        .version(projectsList.get(0).getVersion())
                        .build()
        );
        String keyword = "title";
        String tagIds = "";
        int closed = 1;

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, sort));
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), projectsList.size());
        final Page<Projects> projectsPage = new PageImpl<>(projectsList.subList(start, end), pageRequest, projectsList.size());
        doReturn(projectsPage).when(projectsRepository).findAllByKeyword(keyword, pageRequest);

        // when
        Pair<List<ProjectInfoRes>, Boolean> result = projectsService.getProjectList(sort, pageRequest, keyword, tagIds, closed);

        // then
        assertThat(result.getLeft().get(0).getProjectId()).isEqualTo(1L);
        assertThat(result.getLeft().get(0).getTags().get(0)).isEqualTo(projectInfoRes.get(0).getTags().get(0));
        assertThat(result.getRight()).isFalse();
    }

    @DisplayName("프로젝트 목록 조회 성공 - tag x, closed 포함, 좋아요순")
    @Test
    void getProjectListWithClosedWithoutTagsLike(){
        // given
        List<ProjectTags> tagsList = tagsInit();
        List<ProjectSelectedTags> selectedTagsList = List.of(ProjectSelectedTags.builder()
                .selectedTagsId(2L)
                .tags(tagsList.get(1))
                .projects(project)
                .build());
        List<Projects> projectsList = List.of(
                Projects.builder()
                        .projectsId(1L)
                        .num(1L)
                        .version(1)
                        .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                        .introduction("intro")
                        .title("title")
                        .projectWriter(users)
                        .selectedTags(selectedTagsList)
                        .build()
        );

        String sort = "likeCnt";
        int page = 0;
        String keyword = "title";
        String tagIds = "";
        int closed = 1;
        List<ProjectInfoRes> projectInfoRes = List.of(
                ProjectInfoRes.builder()
                        .date(projectsList.get(0).getModifiedDate())
                        .img(projectsList.get(0).getImg())
                        .projectId(projectsList.get(0).getProjectsId())
                        .feedbackCnt(projectsList.get(0).getFeedbackCnt())
                        .introduction(projectsList.get(0).getIntroduction())
                        .likeCnt(projectsList.get(0).getLikeCnt())
                        .tags(List.of(tagsList.get(1).getName()))
                        .title(projectsList.get(0).getTitle())
                        .version(projectsList.get(0).getVersion())
                        .build()
        );

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, sort));
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), projectsList.size());
        final Page<Projects> projectsPage = new PageImpl<>(projectsList.subList(start, end), pageRequest, projectsList.size());
        doReturn(projectsPage).when(projectsRepository).findAllByKeyword(keyword, pageRequest);

        // when
        Pair<List<ProjectInfoRes>, Boolean> result = projectsService.getProjectList(sort, pageRequest, keyword, tagIds, closed);

        // then
        assertThat(result.getLeft().size()).isEqualTo(1);
        assertThat(result.getLeft().get(0).getProjectId()).isEqualTo(1L);
        assertThat(result.getLeft().get(0).getTags().get(0)).isEqualTo(projectInfoRes.get(0).getTags().get(0));
        assertThat(result.getRight()).isFalse();
    }

    @DisplayName("프로젝트 목록 조회 성공 - tag x, closed 포함, 피드백순")
    @Test
    void getProjectListWithClosedWithoutTagsFeedback(){
        // given
        List<ProjectTags> tagsList = tagsInit();
        List<ProjectSelectedTags> selectedTagsList = List.of(ProjectSelectedTags.builder()
                .selectedTagsId(2L)
                .tags(tagsList.get(1))
                .projects(project)
                .build());
        List<Projects> projectsList = List.of(
                Projects.builder()
                        .projectsId(1L)
                        .num(1L)
                        .version(1)
                        .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                        .introduction("intro")
                        .title("title")
                        .projectWriter(users)
                        .selectedTags(selectedTagsList)
                        .build()
        );

        String sort = "feedbackCnt";
        int page = 0;
        String keyword = "title";
        String tagIds = "";
        int closed = 1;
        List<ProjectInfoRes> projectInfoRes = List.of(
                ProjectInfoRes.builder()
                        .date(projectsList.get(0).getModifiedDate())
                        .img(projectsList.get(0).getImg())
                        .projectId(projectsList.get(0).getProjectsId())
                        .feedbackCnt(projectsList.get(0).getFeedbackCnt())
                        .introduction(projectsList.get(0).getIntroduction())
                        .likeCnt(projectsList.get(0).getLikeCnt())
                        .tags(List.of(tagsList.get(1).getName()))
                        .title(projectsList.get(0).getTitle())
                        .version(projectsList.get(0).getVersion())
                        .build()
        );

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, sort));
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), projectsList.size());
        final Page<Projects> projectsPage = new PageImpl<>(projectsList.subList(start, end), pageRequest, projectsList.size());
        doReturn(projectsPage).when(projectsRepository).findAllByKeyword(keyword, pageRequest);

        // when
        Pair<List<ProjectInfoRes>, Boolean> result = projectsService.getProjectList(sort, pageRequest, keyword, tagIds, closed);

        // then
        assertThat(result.getLeft().size()).isEqualTo(1);
        assertThat(result.getLeft().get(0).getProjectId()).isEqualTo(1L);
        assertThat(result.getLeft().get(0).getTags().get(0)).isEqualTo(projectInfoRes.get(0).getTags().get(0));
        assertThat(result.getRight()).isFalse();
    }

    @DisplayName("프로젝트 목록 조회 성공 - tag x, closed 미포함, 최신순")
    @Test
    void getProjectListWithoutTagsNewest(){
        // given
        List<ProjectTags> tagsList = tagsInit();
        List<ProjectSelectedTags> selectedTagsList = List.of(ProjectSelectedTags.builder()
                .selectedTagsId(2L)
                .tags(tagsList.get(1))
                .projects(project)
                .build());
        List<Projects> projectsList = List.of(
                Projects.builder()
                        .projectsId(1L)
                        .num(1L)
                        .version(1)
                        .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                        .introduction("intro")
                        .title("title")
                        .projectWriter(users)
                        .selectedTags(selectedTagsList)
                        .build()
        );
        List<ProjectInfoRes> projectInfoRes = List.of(
                ProjectInfoRes.builder()
                        .date(projectsList.get(0).getModifiedDate())
                        .img(projectsList.get(0).getImg())
                        .projectId(projectsList.get(0).getProjectsId())
                        .feedbackCnt(projectsList.get(0).getFeedbackCnt())
                        .introduction(projectsList.get(0).getIntroduction())
                        .likeCnt(projectsList.get(0).getLikeCnt())
                        .tags(List.of(tagsList.get(1).getName()))
                        .title(projectsList.get(0).getTitle())
                        .version(projectsList.get(0).getVersion())
                        .build()
        );

        String sort = "modifiedDate";
        int page = 0;
        String keyword = "title";
        String tagIds = "";
        int closed = 0;

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, sort));
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), projectsList.size());
        final Page<Projects> projectsPage = new PageImpl<>(projectsList.subList(start, end), pageRequest, projectsList.size());
        doReturn(projectsPage).when(projectsRepository).findAllOpenedByKeyword(keyword, pageRequest);

        // when
        Pair<List<ProjectInfoRes>, Boolean> result = projectsService.getProjectList(sort, pageRequest, keyword, tagIds, closed);

        // then
        assertThat(result.getLeft().size()).isEqualTo(1);
        assertThat(result.getLeft().get(0).getProjectId()).isEqualTo(1L);
        assertThat(result.getLeft().get(0).getTags().get(0)).isEqualTo(projectInfoRes.get(0).getTags().get(0));
        assertThat(result.getRight()).isFalse();
    }

    @DisplayName("프로젝트 목록 조회 성공 - tag x, closed 미포함, 좋아요순")
    @Test
    void getProjectListWithoutTagsLike(){
        // given
        List<ProjectTags> tagsList = tagsInit();
        List<ProjectSelectedTags> selectedTagsList = List.of(ProjectSelectedTags.builder()
                .selectedTagsId(2L)
                .tags(tagsList.get(1))
                .projects(project)
                .build());
        List<Projects> projectsList = List.of(
                Projects.builder()
                        .projectsId(1L)
                        .num(1L)
                        .version(1)
                        .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                        .introduction("intro")
                        .title("title")
                        .projectWriter(users)
                        .selectedTags(selectedTagsList)
                        .build()
        );
        List<ProjectInfoRes> projectInfoRes = List.of(
                ProjectInfoRes.builder()
                        .date(projectsList.get(0).getModifiedDate())
                        .img(projectsList.get(0).getImg())
                        .projectId(projectsList.get(0).getProjectsId())
                        .feedbackCnt(projectsList.get(0).getFeedbackCnt())
                        .introduction(projectsList.get(0).getIntroduction())
                        .likeCnt(projectsList.get(0).getLikeCnt())
                        .tags(List.of(tagsList.get(1).getName()))
                        .title(projectsList.get(0).getTitle())
                        .version(projectsList.get(0).getVersion())
                        .build()
        );

        String sort = "likeCnt";
        int page = 0;
        String keyword = "title";
        String tagIds = "";
        int closed = 0;

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, sort));
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), projectsList.size());
        final Page<Projects> projectsPage = new PageImpl<>(projectsList.subList(start, end), pageRequest, projectsList.size());
        doReturn(projectsPage).when(projectsRepository).findAllOpenedByKeyword(keyword, pageRequest);

        // when
        Pair<List<ProjectInfoRes>, Boolean> result = projectsService.getProjectList(sort, pageRequest, keyword, tagIds, closed);

        // then
        assertThat(result.getLeft().size()).isEqualTo(1);
        assertThat(result.getLeft().get(0).getProjectId()).isEqualTo(1L);
        assertThat(result.getLeft().get(0).getTags().get(0)).isEqualTo(projectInfoRes.get(0).getTags().get(0));
        assertThat(result.getRight()).isFalse();
    }

    @DisplayName("프로젝트 목록 조회 성공 - tag x, closed 미포함, 피드백순")
    @Test
    void getProjectListWithoutTagsFeedback(){
        // given
        List<ProjectTags> tagsList = tagsInit();
        List<ProjectSelectedTags> selectedTagsList = List.of(ProjectSelectedTags.builder()
                .selectedTagsId(2L)
                .tags(tagsList.get(1))
                .projects(project)
                .build());
        List<Projects> projectsList = List.of(
                Projects.builder()
                        .projectsId(1L)
                        .num(1L)
                        .version(1)
                        .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                        .introduction("intro")
                        .title("title")
                        .projectWriter(users)
                        .selectedTags(selectedTagsList)
                        .build()
        );
        List<ProjectInfoRes> projectInfoRes = List.of(
                ProjectInfoRes.builder()
                        .date(projectsList.get(0).getModifiedDate())
                        .img(projectsList.get(0).getImg())
                        .projectId(projectsList.get(0).getProjectsId())
                        .feedbackCnt(projectsList.get(0).getFeedbackCnt())
                        .introduction(projectsList.get(0).getIntroduction())
                        .likeCnt(projectsList.get(0).getLikeCnt())
                        .tags(List.of(tagsList.get(1).getName()))
                        .title(projectsList.get(0).getTitle())
                        .version(projectsList.get(0).getVersion())
                        .build()
        );

        String sort = "feedbackCnt";
        int page = 0;
        String keyword = "title";
        String tagIds = "";
        int closed = 0;

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, sort));
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), projectsList.size());
        final Page<Projects> projectsPage = new PageImpl<>(projectsList.subList(start, end), pageRequest, projectsList.size());
        doReturn(projectsPage).when(projectsRepository).findAllOpenedByKeyword(keyword, pageRequest);

        // when
        Pair<List<ProjectInfoRes>, Boolean> result = projectsService.getProjectList(sort, pageRequest, keyword, tagIds, closed);

        // then
        assertThat(result.getLeft().size()).isEqualTo(1);
        assertThat(result.getLeft().get(0).getProjectId()).isEqualTo(1L);
        assertThat(result.getLeft().get(0).getTags().get(0)).isEqualTo(projectInfoRes.get(0).getTags().get(0));
        assertThat(result.getRight()).isFalse();
    }

    @DisplayName("프로젝트 목록 조회 성공 - tag o, closed 포함, 최신순")
    @Test
    void getProjectListWithTagsNewest(){
        // given
        List<ProjectTags> tagsList = tagsInit();
        List<ProjectSelectedTags> selectedTagsList = List.of(ProjectSelectedTags.builder()
                .selectedTagsId(2L)
                .tags(tagsList.get(1))
                .projects(project)
                .build());
        List<Projects> projectsList = List.of(
                Projects.builder()
                        .projectsId(1L)
                        .num(1L)
                        .version(1)
                        .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                        .introduction("intro")
                        .title("title")
                        .projectWriter(users)
                        .selectedTags(selectedTagsList)
                        .build()
        );

        String sort = "modifiedDate";
        int page = 0;
        String keyword = "title";
        String tagIds = "2";
        int closed = 1;

        List<ProjectInfoRes> projectInfoRes = List.of(
                ProjectInfoRes.builder()
                        .date(projectsList.get(0).getModifiedDate())
                        .img(projectsList.get(0).getImg())
                        .projectId(projectsList.get(0).getProjectsId())
                        .feedbackCnt(projectsList.get(0).getFeedbackCnt())
                        .introduction(projectsList.get(0).getIntroduction())
                        .likeCnt(projectsList.get(0).getLikeCnt())
                        .tags(List.of(tagsList.get(1).getName()))
                        .title(projectsList.get(0).getTitle())
                        .version(projectsList.get(0).getVersion())
                        .build()
        );

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, sort));
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), projectsList.size());
        final Page<Projects> projectsPage = new PageImpl<>(projectsList.subList(start, end), pageRequest, projectsList.size());
        doReturn(projectsPage).when(projectSelectedTagsRepository).findAllByKeywordAndTag(keyword, List.of(2L), 1L, pageRequest);

        // when
        Pair<List<ProjectInfoRes>, Boolean> result = projectsService.getProjectList(sort, pageRequest, keyword, tagIds, closed);

        // then
        assertThat(result.getLeft().size()).isEqualTo(1);
        assertThat(result.getLeft().get(0).getProjectId()).isEqualTo(1L);
        assertThat(result.getLeft().get(0).getTags().get(0)).isEqualTo(projectInfoRes.get(0).getTags().get(0));
        assertThat(result.getRight()).isFalse();
    }

    @DisplayName("프로젝트 목록 조회 성공 - tag o, closed 미포함, 최신순")
    @Test
    void getProjectListWithoutClosedWithTagsNewest(){
        // given
        List<ProjectTags> tagsList = tagsInit();
        List<ProjectSelectedTags> selectedTagsList = List.of(ProjectSelectedTags.builder()
                .selectedTagsId(2L)
                .tags(tagsList.get(1))
                .projects(project)
                .build());
        List<Projects> projectsList = List.of(
                Projects.builder()
                        .projectsId(1L)
                        .num(1L)
                        .version(1)
                        .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                        .introduction("intro")
                        .title("title")
                        .projectWriter(users)
                        .selectedTags(selectedTagsList)
                        .build()
        );

        String sort = "modifiedDate";
        int page = 0;
        String keyword = "title";
        String tagIds = "2";
        int closed = 0;

        List<ProjectInfoRes> projectInfoRes = List.of(
                ProjectInfoRes.builder()
                        .date(projectsList.get(0).getModifiedDate())
                        .img(projectsList.get(0).getImg())
                        .projectId(projectsList.get(0).getProjectsId())
                        .feedbackCnt(projectsList.get(0).getFeedbackCnt())
                        .introduction(projectsList.get(0).getIntroduction())
                        .likeCnt(projectsList.get(0).getLikeCnt())
                        .tags(List.of(tagsList.get(1).getName()))
                        .title(projectsList.get(0).getTitle())
                        .version(projectsList.get(0).getVersion())
                        .build()
        );

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, sort));
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), projectsList.size());
        final Page<Projects> projectsPage = new PageImpl<>(projectsList.subList(start, end), pageRequest, projectsList.size());
        doReturn(projectsPage).when(projectSelectedTagsRepository).findAllOpenedByKeywordAndTag(keyword, List.of(2L), 1L, pageRequest);

        // when
        Pair<List<ProjectInfoRes>, Boolean> result = projectsService.getProjectList(sort, pageRequest, keyword, tagIds, closed);

        // then
        assertThat(result.getLeft().size()).isEqualTo(1);
        assertThat(result.getLeft().get(0).getProjectId()).isEqualTo(1L);
        assertThat(result.getLeft().get(0).getTags().get(0)).isEqualTo(projectInfoRes.get(0).getTags().get(0));
        assertThat(result.getRight()).isFalse();
    }

    @DisplayName("프로젝트 삭제 성공")
    @Test
    void deleteProjectSucceed(){
        // given
        List<ProjectSelectedTags> selectedTagsList = selectedTagsInit();
        Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .selectedTags(selectedTagsList)
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(project).when(projectsRepository).findLatestProject(any(Long.class), any(Long.class));
        doReturn(info).when(projectsInfoRepository).findByProjects(any(Projects.class));

        // when
        int success = projectsService.deleteProject(project.getProjectsId(), users.getUsersId());

        // then
        assertThat(success).isEqualTo(1);
    }

    @DisplayName("프로젝트 삭제 실패 - 일치하는 유저 없음")
    @Test
    void deleteProjectFailNoUser(){
        // given
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.deleteProject(1L, 1L)
        );

        assertEquals("일치하는 유저가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("프로젝트 삭제 실패 - 일치하는 프로젝트 없음")
    @Test
    void deleteProjectFailNoProject(){
        // given
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(projectsRepository).findByProjectsId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.deleteProject(1L, 1L)
        );

        assertEquals("일치하는 프로젝트가 존재하지 않습니다", exception.getMessage());
    }


    @DisplayName("프로젝트 삭제 실패 - 최신 버전의 프로젝트가 아님")
    @Test
    void deleteProjectFailNotNewest(){
        // given
        List<ProjectSelectedTags> selectedTagsList = selectedTagsInit();
        Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .selectedTags(selectedTagsList)
                .build();
        Projects project2 = Projects.builder()
                .projectsId(2L)
                .num(2L)
                .version(2)
                .img("https://roughcode.s3.ap-northeast-2.amazonaws.com/project/7_1")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .selectedTags(selectedTagsList)
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(project2).when(projectsRepository).findLatestProject(any(Long.class), any(Long.class));

        // when & then
        NotNewestVersionException exception = assertThrows(
                NotNewestVersionException.class, () -> projectsService.deleteProject(1L, 1L)
        );

        assertEquals("최신 버전이 아닙니다", exception.getMessage());
    }

    @DisplayName("프로젝트 코드 연결 성공")
    @Test
    void connectProjectCodeSucceed(){
        // given
        List<Codes> codesList = codeInit();
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(info).when(projectsInfoRepository).findByProjects(any(Projects.class));
        doReturn(codesList.get(0)).when(codesRepository).findByCodesId(any(Long.class));

        // when
        int success = projectsService.connect(project.getProjectsId(), users.getUsersId(), List.of(0L, 1L, 2L));

        // then
        assertThat(success).isEqualTo(codesList.size());
    }

    @DisplayName("프로젝트 코드 연결 실패 - 일치하는 프로젝트 없음")
    @Test
    void connectProjectCodeFailNoProject(){
        // given
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(projectsRepository).findByProjectsId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.connect(1L, 1L, List.of(0L, 1L, 2L))
        );
        
        assertEquals("일치하는 프로젝트가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("프로젝트 코드 연결 실패 - 일치하는 코드 없음")
    @Test
    void connectProjectCodeFailNoCode(){
        // given
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(info).when(projectsInfoRepository).findByProjects(any(Projects.class));
        doReturn(null).when(codesRepository).findByCodesId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.connect(1L, 1L, List.of(0L, 1L, 2L))
        );

        assertEquals("일치하는 코드가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("프로젝트 코드 연결 실패 - code writer와 등록하려는 user가 일치하지 않음")
    @Test
    void connectProjectCodeFailUserDiffer(){
        // given
        doReturn(users2).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(info).when(projectsInfoRepository).findByProjects(any(Projects.class));

        // when & then
        NotMatchException exception = assertThrows(
                NotMatchException.class, () -> projectsService.connect(1L, 1L, List.of(0L, 1L, 2L))
        );

        assertEquals("접근 권한이 없습니다", exception.getMessage());
    }


    @DisplayName("프로젝트 수정 성공")
    @Test
    void updateProjectSucceed() throws Exception {
        // given
        List<ProjectTags> tagsList = tagsInit();
        List<Feedbacks> feedbacksList = feedbacksInit();

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
        doReturn(feedbacksList.get(0)).when(feedbacksRepository).findByFeedbacksId(any(Long.class));

        // when
        int success = projectsService.updateProject(req, 1L);

        // then
        assertThat(success).isEqualTo(1);
    }

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
        MockMultipartFile thumbnail = getImage();

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
        MockMultipartFile thumbnail = getImage();
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.updateProjectThumbnail(thumbnail, 1L, 1L)
        );

        assertEquals("일치하는 유저가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("프로젝트 썸네일 등록 실패 - 일치하는 프로젝트가 없음")
    @Test
    void updateProjectThumbnailFailNoProject() throws IOException {
        MockMultipartFile thumbnail = getImage();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(projectsRepository).findByProjectsId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.updateProjectThumbnail(thumbnail, 1L, 1L)
        );

        assertEquals("일치하는 프로젝트가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("프로젝트 썸네일 등록 실패 - 등록하려는 유저와 프로젝트의 유저가 일치하지 않음")
    @Test
    void updateProjectThumbnailFailUserDiffer() throws IOException {

        MockMultipartFile thumbnail = getImage();
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
    void insertProjectSucceed() throws MessagingException, IOException {
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

        // when
        Long success = projectsService.insertProject(req, 1L);

        // then
        assertThat(success).isEqualTo(1L);
    }

    @DisplayName("프로젝트 등록 성공 - 기존 프로젝트 업데이트")
    @Test
    void insertProjectSucceedVersionUp() throws MessagingException, IOException {
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

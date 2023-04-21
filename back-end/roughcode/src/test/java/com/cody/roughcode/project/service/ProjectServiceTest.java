package com.cody.roughcode.project.service;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.repository.CodesRepostiory;
import com.cody.roughcode.project.dto.req.ProjectReq;
import com.cody.roughcode.project.entity.ProjectSelectedTags;
import com.cody.roughcode.project.entity.ProjectTags;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.entity.ProjectsInfo;
import com.cody.roughcode.project.repository.ProjectSelectedTagsRepository;
import com.cody.roughcode.project.repository.ProjectTagsRepository;
import com.cody.roughcode.project.repository.ProjectsInfoRepository;
import com.cody.roughcode.project.repository.ProjectsRepository;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

    final Users users = Users.builder()
            .usersId(1L)
            .email("kosy1782@gmail.com")
            .name("고수")
            .roles(List.of(String.valueOf(ROLE_USER)))
            .build();


    public static MockMultipartFile convert(String imageUrl, String imageName) throws Exception { // string to MultiPartFile

        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream inputStream = connection.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        byte[] imageBytes = outputStream.toByteArray();

        ByteArrayResource resource = new ByteArrayResource(imageBytes);
        return new MockMultipartFile("file", imageName, null, resource.getByteArray());
    }

    @DisplayName("프로젝트 등록 성공 - 새 프로젝트")
    @Test
    void insertProjectSucceed() throws Exception {
        // given
        List<ProjectTags> tagsList = tagsInit();
        ProjectReq req = ProjectReq.builder()
                .codesId((long) -1)
                .projectId((long) -1)
                .title("title")
                .url("https://www.google.com")
                .introduction("introduction")
                .selectedTagsId(List.of(1L))
                .content("content")
                .notice("notice")
                .build();

        MultipartFile thumbnail = convert("https://www.linkpicture.com/q/KakaoTalk_20230413_101644169.png", "logo");

        List<String> fileNames = List.of("1", "1");

        String imgUrl = s3FileService.upload(thumbnail, "project", fileNames);

        Projects project = Projects.builder()
                .num(1L)
                .version(1)
                .img(imgUrl)
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
        doReturn("https://www.linkpicture.com/q/KakaoTalk_20230413_101644169.png").when(s3FileService).upload(thumbnail, "project", fileNames);
        doReturn(null).when(codesRepostiory).findByCodesId((long)-1);
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
        int success = projectsService.insertProject(req, thumbnail, 1L);

        // then
        assertThat(success).isEqualTo(1);
    }

    @DisplayName("프로젝트 등록 성공 - 기존 프로젝트 업데이트")
    @Test
    void insertProjectSucceedVersionUp() throws Exception {
        // given
        List<ProjectTags> tagsList = tagsInit();
        ProjectReq req = ProjectReq.builder()
                .codesId((long) -1)
                .projectId((long) 1)
                .title("title")
                .url("https://www.google.com")
                .introduction("introduction")
                .selectedTagsId(List.of(1L))
                .content("content")
                .notice("notice")
                .build();

        MultipartFile thumbnail = convert("https://www.linkpicture.com/q/KakaoTalk_20230413_101644169.png", "logo");

        List<String> fileNames = List.of("1", "2");

        String imgUrl = s3FileService.upload(thumbnail, "project", fileNames);

        Projects project = Projects.builder()
                .num(1L)
                .version(2)
                .img(imgUrl)
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
                .img(imgUrl)
                .introduction(req.getIntroduction())
                .title(req.getTitle())
                .projectWriter(users)
                .projectsCodes(new ArrayList<>())
                .build();

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(original).when(projectsRepository).findProjectWithMaxVersionByProjectsId(1L);
        doReturn("https://www.linkpicture.com/q/KakaoTalk_20230413_101644169.png").when(s3FileService).upload(thumbnail, "project", fileNames);
        doReturn(null).when(codesRepostiory).findByCodesId((long)-1);
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
        int success = projectsService.insertProject(req, thumbnail, 1L);

        // then
        assertThat(success).isEqualTo(1);
    }

    @DisplayName("프로젝트 등록 실패 - 존재하지 않는 유저 아이디")
    @Test
    void insertProjectFailNoUser() throws Exception {
        // given
        ProjectReq req = ProjectReq.builder()
                .codesId((long) -1)
                .projectId(1L)
                .title("title")
                .url("https://www.google.com")
                .introduction("introduction")
                .selectedTagsId(List.of(1L))
                .content("content")
                .notice("notice")
                .build();
        MultipartFile thumbnail = convert("https://www.linkpicture.com/q/KakaoTalk_20230413_101644169.png", "logo");

        // when & then
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.insertProject(req, thumbnail, 1L)
        );

        assertEquals("일치하는 유저가 존재하지 않습니다.", exception.getMessage());
    }

    @DisplayName("프로젝트 등록 실패 - 존재하지 않는 project id")
    @Test
    void insertProjectFailNoProject() throws Exception {
        // given
        ProjectReq req = ProjectReq.builder()
                .codesId((long) -1)
                .projectId(1L)
                .title("title")
                .url("https://www.google.com")
                .introduction("introduction")
                .selectedTagsId(List.of(1L))
                .content("content")
                .notice("notice")
                .build();
        MultipartFile thumbnail = convert("https://www.linkpicture.com/q/KakaoTalk_20230413_101644169.png", "logo");

        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(projectsRepository).findProjectWithMaxVersionByProjectsId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> projectsService.insertProject(req, thumbnail, 1L)
        );

        assertEquals("일치하는 프로젝트가 존재하지 않습니다.", exception.getMessage());
    }

    private List<Codes> codesInit() {
        List<Codes> codesList = new ArrayList<>();
        for (long i = 1L; i <= 3L; i++) {
            codesList.add(Codes.builder()
                    .codesId(i)
                    .title("title")
                    .num(i)
                    .version((int) i)
                    .codeWriter(users)
                    .build());
        }

        return codesList;
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


}

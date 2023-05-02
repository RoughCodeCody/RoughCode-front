package com.cody.roughcode.mypage.service;

import com.cody.roughcode.project.dto.res.ProjectInfoRes;
import com.cody.roughcode.project.entity.ProjectSelectedTags;
import com.cody.roughcode.project.entity.ProjectTags;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.repository.ProjectFavoritesRepository;
import com.cody.roughcode.project.repository.ProjectsRepository;
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

import java.util.ArrayList;
import java.util.List;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class MypageServiceTest {
    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }

    @InjectMocks
    private MypageServiceImpl mypageService;

    @Mock
    private ProjectsRepository projectsRepository;
    @Mock
    private ProjectFavoritesRepository projectFavoritesRepository;
    @Mock
    private UsersRepository usersRepository;

    final Users users = Users.builder()
            .usersId(1L)
            .email("kosy1782@gmail.com")
            .name("kosy318")
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

    @DisplayName("피드백 남긴 프로젝트 목록 조회 성공")
    @Test
    void getFeedbackProjectListSucceed(){
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

        PageRequest pageRequest = PageRequest.of(page, 10);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), projectsList.size());
        final Page<Projects> projectsPage = new PageImpl<>(projectsList.subList(start, end), pageRequest, projectsList.size());
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(projectsPage).when(projectsRepository).findAllMyFeedbacks(users.getUsersId(), pageRequest);

        // when
        Pair<List<ProjectInfoRes>, Boolean> result = mypageService.getFeedbackProjectList(pageRequest, users.getUsersId());

        // then
        assertThat(result.getLeft().get(0).getProjectId()).isEqualTo(1L);
        assertThat(result.getRight()).isFalse();
    }

    @DisplayName("내 즐겨찾기 프로젝트 목록 조회 성공")
    @Test
    void getFavoriteProjectListSucceed(){
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

        PageRequest pageRequest = PageRequest.of(page, 10);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), projectsList.size());
        final Page<Projects> projectsPage = new PageImpl<>(projectsList.subList(start, end), pageRequest, projectsList.size());
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(projectsPage).when(projectsRepository).findAllMyFavorite(users.getUsersId(), pageRequest);

        // when
        Pair<List<ProjectInfoRes>, Boolean> result = mypageService.getFavoriteProjectList(pageRequest, users.getUsersId());

        // then
        assertThat(result.getLeft().get(0).getProjectId()).isEqualTo(1L);
        assertThat(result.getRight()).isFalse();
    }

    @DisplayName("내 즐겨찾기 프로젝트 목록 조회 실패 - 일치하는 유저 없음")
    @Test
    void getFavoriteProjectListFailNoUser(){
        // given
        int page = 0;

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "modifiedDate"));
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> mypageService.getFavoriteProjectList(pageRequest, users.getUsersId())
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 유저가 존재하지 않습니다");
    }

    @DisplayName("내 프로젝트 목록 조회 성공")
    @Test
    void getProjectListSucceed(){
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

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "modifiedDate"));
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), projectsList.size());
        final Page<Projects> projectsPage = new PageImpl<>(projectsList.subList(start, end), pageRequest, projectsList.size());
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(projectsPage).when(projectsRepository).findAllByProjectWriter(users.getUsersId(), pageRequest);

        // when
        Pair<List<ProjectInfoRes>, Boolean> result = mypageService.getProjectList(pageRequest, users.getUsersId());

        // then
        assertThat(result.getLeft().get(0).getProjectId()).isEqualTo(1L);
        assertThat(result.getRight()).isFalse();
    }

}

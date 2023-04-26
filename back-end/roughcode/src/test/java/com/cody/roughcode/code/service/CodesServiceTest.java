package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.CodeReq;
import com.cody.roughcode.code.entity.*;
import com.cody.roughcode.code.repository.*;
import com.cody.roughcode.project.entity.ProjectSelectedTags;
import com.cody.roughcode.project.entity.ProjectTags;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.entity.ProjectsInfo;
import com.cody.roughcode.project.repository.ProjectsRepository;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class) // 가짜 객체 주입을 사용
class CodesServiceTest {

    @InjectMocks
    private CodesServiceImpl codesService;

    @Mock
    private CodesRepository codesRepository;
    @Mock
    private CodesInfoRepository codesInfoRepository;
    @Mock
    private CodeTagsRepository codeTagsRepository;
    @Mock
    private CodeSelectedTagsRepository codeSelectedTagsRepository;
    @Mock
    private ProjectsRepository projectsRepository;
    @Mock
    private ReviewsRepository reviewsRepository;
    @Mock
    private SelectedReviewsRepository selectedReviewsRepository;
    @Mock
    private UsersRepository usersRepository;

    final Users user = Users.builder()
            .usersId(1L)
            .email("cody306@gmail.com")
            .name("코디")
            .roles(List.of(String.valueOf(ROLE_USER)))
            .build();

    final Users user2 = Users.builder()
            .usersId(2L)
            .email("cody306@gmail.com")
            .name("코디")
            .roles(List.of(String.valueOf(ROLE_USER)))
            .build();
    
    final Codes code = Codes.builder()
            .codesId(1L)
            .num(1L)
            .version(1)
            .title("개발새발 코드")
            .codeWriter(user)
            .build();

    final CodesInfo info = CodesInfo.builder()
            .githubUrl("https://api.github.com/repos/cody/hello-world/contents/src/main.py")
            .content("시간초과 뜹니다")
            .build();

    private List<CodeTags> tagsInit(){
        List<CodeTags> tagsList = new ArrayList<>();
        for(Long i = 1L; i<=10L; i++) {
            tagsList.add(CodeTags.builder()
                    .tagsId(i)
                    .name("tag"+i.toString())
                    .build());
        }
        return tagsList;
    }

    private List<CodeSelectedTags> selectedTagsList(){
        List<CodeTags> tags = tagsInit();
        List<CodeSelectedTags> tagsList = new ArrayList<>();
        for(Long i=1L; i<=3L; i++){
            tagsList.add(CodeSelectedTags.builder()
                    .selectedTagsId(i)
                    .tags(tags.get(i.intValue()))
                    .codes(code)
                    .build());
        }
        return tagsList;
    }

    private Projects project = Projects.builder()
            .projectsId(1L)
            .projectWriter(user)
            .num(2L)
            .version(1)
            .img("개발새발 프로젝트 이미지 URL")
            .introduction("개발새발 프로젝트 소개")
            .build();

    @DisplayName("코드 등록 성공 - 새 코드")
    @Test
    void insertCodeSucceed() {
        // given
        List<CodeTags> tagsList = tagsInit();
        CodeReq req = CodeReq.builder()
                .codeId(-1L)
                .title("개발새발 코드")
                .selectedTagsId(List.of(1L))
                .githubUrl("https://api.github.com/repos/cody/hello-world/contents/src/main.py")
                .content("시간초과 뜹니다")
                .projectId(1L)
                .build();

        CodeSelectedTags selectedTags = CodeSelectedTags.builder()
                .tags(tagsList.get(0))
                .codes(code)
                .build();

        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(user).when(usersRepository).save(any(Users.class));
        doReturn(code).when(codesRepository).save(any(Codes.class));
        doReturn(tagsList.get(0)).when(codeTagsRepository).findByTagsId(any(Long.class));
        doReturn(selectedTags)
                .when(codeSelectedTagsRepository)
                .save(any(CodeSelectedTags.class));
        doReturn(tagsList.get(0)).when(codeTagsRepository).save(any(CodeTags.class));
        doReturn(info).when(codesInfoRepository).save(any(CodesInfo.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));

        // when
        Long successCodeId = codesService.insertCode(req, 1L);

        // then
        assertThat(successCodeId).isEqualTo(1L);
    }

    @DisplayName("코드 등록 성공 - 기존 코드 업데이트")
    @Test
    void insertCodeSucceedVersionUp() {
        List<CodeTags> tagsList = tagsInit();
        CodeReq req = CodeReq.builder()
                .codeId(2L)
                .title("개발새발 코드2")
                .selectedTagsId(List.of(1L))
                .selectedReviewsId(List.of(1L))
                .githubUrl("https://api.github.com/repos/cody/hello-world/contents/src/main.py")
                .content("조금 더 효율적인 방법으로 풀었습니다.")
                .projectId(1L)
                .build();
        CodeSelectedTags selectedTags = CodeSelectedTags.builder()
                .tags(tagsList.get(0))
                .codes(code)
                .build();
        Reviews review = Reviews.builder()
                .reviewsId(1L)
                .users(user)
                .codes(code)
                .content("최소힙을 사용해보세요.")
                .lineNumbers("1,3,4,5")
                .reviewCode("리뷰리뷰")
                .build();
        SelectedReviews selectedReviews = SelectedReviews.builder()
                .selectedReviewsId(1L)
                .reviews(review)
                .codes(code)
                .build();
        Codes updatedCode = Codes.builder()
                .codesId(2L)
                .num(1L)
                .version(2)
                .title("개발새발 코드2")
                .codeWriter(user)
                .build();

        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
//        doReturn(user).when(usersRepository).save(any(Users.class));
        doReturn(updatedCode).when(codesRepository).save(any(Codes.class));
        doReturn(code).when(codesRepository).findLatestByCodesId(any(Long.class));
        doReturn(tagsList.get(0)).when(codeTagsRepository).findByTagsId(any(Long.class));
        doReturn(selectedTags).when(codeSelectedTagsRepository).save(any(CodeSelectedTags.class));
        doReturn(selectedReviews).when(selectedReviewsRepository).save(any(SelectedReviews.class));
        doReturn(tagsList.get(0)).when(codeTagsRepository).save(any(CodeTags.class));
        doReturn(info).when(codesInfoRepository).save(any(CodesInfo.class));
        doReturn(project).when(projectsRepository).findByProjectsId(any(Long.class));
        doReturn(review).when(reviewsRepository).findByReviewsId(any(Long.class));

        // when
        Long successCodeId = codesService.insertCode(req, 1L);

        // then
        assertThat(successCodeId).isEqualTo(2L);
    }

    @DisplayName("코드 등록 실패 - 존재하지 않는 유저 아이디")
    @Test
    void insertCodeFailNoUser() {

    }

    @DisplayName("코드 등록 실패 - code 작성한 user랑 version up 하려는 user가 다름")
    @Test
    void insertCodeFailUserDiffer() {

    }
}
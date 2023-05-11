package com.cody.roughcode.mypage.service;

import com.cody.roughcode.code.dto.res.CodeInfoRes;
import com.cody.roughcode.code.entity.CodeSelectedTags;
import com.cody.roughcode.code.entity.CodeTags;
import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.repository.CodeLikesRepository;
import com.cody.roughcode.code.repository.CodesRepository;
import com.cody.roughcode.code.repository.ReviewsRepository;
import com.cody.roughcode.code.repository.SelectedReviewsRepository;
import com.cody.roughcode.project.dto.res.ProjectInfoRes;
import com.cody.roughcode.project.entity.ProjectSelectedTags;
import com.cody.roughcode.project.entity.ProjectTags;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.repository.FeedbacksRepository;
import com.cody.roughcode.project.repository.ProjectFavoritesRepository;
import com.cody.roughcode.project.repository.ProjectsRepository;
import com.cody.roughcode.project.repository.SelectedFeedbacksRepository;
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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private CodesRepository codesRepository;
    @Mock
    private CodeLikesRepository codeLikesRepository;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private FeedbacksRepository feedbackRepository;
    @Mock
    private ReviewsRepository reviewsRepository;
    @Mock
    private SelectedFeedbacksRepository selectedFeedbacksRepository;
    @Mock
    private SelectedReviewsRepository selectedReviewsRepository;

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
    
    final Codes code = Codes.builder()
            .codesId(1L)
            .num(1L)
            .version(1)
            .codeWriter(users)
            .title("title")
            .reviewCnt(1)
            .build(); 

    private List<ProjectTags> projectTagsInit() {
        List<ProjectTags> tagsList = new ArrayList<>();
        for (long i = 1L; i <= 3L; i++) {
            tagsList.add(ProjectTags.builder()
                    .tagsId(i)
                    .name("tag1")
                    .build());
        }

        return tagsList;
    }
    
    private List<CodeTags> codeTagsInit() {
        List<CodeTags> tagsList = new ArrayList<>();
        for (long i = 1L; i <= 3L; i++) {
            tagsList.add(CodeTags.builder()
                    .tagsId(i)
                    .name("tag1")
                    .build());
        }

        return tagsList;
    }

    final String statCard = "<svg viewBox=\"0 0 1030 445\" xmlns=\"http://www.w3.org/2000/svg\">    <style>      .small {        font: italic 13px sans-serif;      }      .heavy {        font: bold 30px sans-serif;      }              .Rrrrr {        font: italic 40px serif;        fill: red;      }      .title {        font: 800 30px 'Segoe UI', Ubuntu, Sans-Serif;        fill: #319795;        animation: fadeInAnimation 0.8s ease-in-out forwards;      }      .content {        font: 800 20px 'Segoe UI', Ubuntu, Sans-Serif;        fill: #45474F;        animation: fadeInAnimation 0.8s ease-in-out forwards;      }    </style>    <rect        data-testid=\"card-bg\"        x=\"1%\"        y=\"0.5%\"        rx=\"25\"        height=\"99%\"        stroke=\"#319795\"        width=\"98%\"        fill=\"#ffffff\"      />      <rect      data-testid=\"card-bg\"      x=\"7%\"      y=\"20%\"      rx=\"5\"      height=\"70%\"      width=\"86%\"      fill=\"#EFF8FF\"    />       <text x=\"7%\" y=\"13%\" class=\"title\">${title}</text>    <text x=\"14%\" y=\"32%\" class=\"content\">1. 프로젝트 피드백 횟수:</text>    <text x=\"14%\" y=\"42%\" class=\"content\">2. 코드 리뷰 횟수:</text>    <text x=\"14%\" y=\"52%\" class=\"content\">3. 반영된 프로젝트 피드백 수:</text>    <text x=\"14%\" y=\"62%\" class=\"content\">4. 반영된 코드 리뷰 수:</text>    <text x=\"14%\" y=\"72%\" class=\"content\">5. 프로젝트 리팩토링 횟수:</text>    <text x=\"14%\" y=\"82%\" class=\"content\">6. 코드 리팩토링 횟수:</text>    <text x=\"64%\" y=\"32%\" class=\"content\">${feedbackCnt}</text>    <text x=\"64%\" y=\"42%\" class=\"content\">${codeReviewCnt}</text>    <text x=\"64%\" y=\"52%\" class=\"content\">${includedFeedbackCnt}</text>    <text x=\"64%\" y=\"62%\" class=\"content\">${includedCodeReviewCnt}</text>    <text x=\"64%\" y=\"72%\" class=\"content\">${projectRefactorCnt}</text>    <text x=\"64%\" y=\"82%\" class=\"content\">${codeRefactorCnt}</text>  </svg>";

    @DisplayName("스탯 카드에 유저 아이디를 가지고 정보 넣기 성공")
    @Test
    void makeStackCardWithUserIdSucceed() throws FileNotFoundException {
        // given
        doReturn(users).when(usersRepository).findByUsersId(1L);
        doReturn(1).when(feedbackRepository).countByUsers(any(Users.class));
        doReturn(2).when(reviewsRepository).countByUsers(any(Users.class));
        doReturn(3).when(selectedFeedbacksRepository).countByUsers(any(Users.class));
        doReturn(4).when(selectedReviewsRepository).countByUsers(any(Users.class));
        doReturn(5).when(projectsRepository).countByProjectWriter(any(Users.class));
        doReturn(6).when(codesRepository).countByCodeWriter(any(Users.class));

        // when
        String completeCard = mypageService.makeStatCardWithUserId(users.getUsersId());

        // then
        assertThat(completeCard).isEqualTo("<svg viewBox=\"0 0 1030 445\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "    <style>\n" +
                "      .small {\n" +
                "        font: italic 13px sans-serif;\n" +
                "      }\n" +
                "      .heavy {\n" +
                "        font: bold 30px sans-serif;\n" +
                "      }\n" +
                "  \n" +
                "      /* Note that the color of the text is set with the    *\n" +
                "       * fill property, the color property is for HTML only */\n" +
                "      .Rrrrr {\n" +
                "        font: italic 40px serif;\n" +
                "        fill: red;\n" +
                "      }\n" +
                "      .title {\n" +
                "        font: 800 30px 'Segoe UI', Ubuntu, Sans-Serif;\n" +
                "        fill: #319795;\n" +
                "        animation: fadeInAnimation 0.8s ease-in-out forwards;\n" +
                "      }\n" +
                "      .content {\n" +
                "        font: 800 20px 'Segoe UI', Ubuntu, Sans-Serif;\n" +
                "        fill: #45474F;\n" +
                "        animation: fadeInAnimation 0.8s ease-in-out forwards;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <rect\n" +
                "        data-testid=\"card-bg\"\n" +
                "        x=\"1%\"\n" +
                "        y=\"0.5%\"\n" +
                "        rx=\"25\"\n" +
                "        height=\"99%\"\n" +
                "        stroke=\"#319795\"\n" +
                "        width=\"98%\"\n" +
                "        fill=\"#ffffff\"\n" +
                "      />\n" +
                "\n" +
                "      <rect\n" +
                "      data-testid=\"card-bg\"\n" +
                "      x=\"7%\"\n" +
                "      y=\"20%\"\n" +
                "      rx=\"5\"\n" +
                "      height=\"70%\"\n" +
                "      width=\"86%\"\n" +
                "      fill=\"#EFF8FF\"\n" +
                "    /> \n" +
                "  \n" +
                "    <text x=\"7%\" y=\"13%\" class=\"title\">${title}</text>\n" +
                "\n" +
                "    <text x=\"14%\" y=\"32%\" class=\"content\">1. 프로젝트 피드백 횟수:</text>\n" +
                "    <text x=\"14%\" y=\"42%\" class=\"content\">2. 코드 리뷰 횟수:</text>\n" +
                "    <text x=\"14%\" y=\"52%\" class=\"content\">3. 반영된 프로젝트 피드백 수:</text>\n" +
                "    <text x=\"14%\" y=\"62%\" class=\"content\">4. 반영된 코드 리뷰 수:</text>\n" +
                "    <text x=\"14%\" y=\"72%\" class=\"content\">5. 프로젝트 리팩토링 횟수:</text>\n" +
                "    <text x=\"14%\" y=\"82%\" class=\"content\">6. 코드 리팩토링 횟수:</text>\n" +
                "\n" +
                "    <text x=\"64%\" y=\"32%\" class=\"content\">1</text>\n" +
                "    <text x=\"64%\" y=\"42%\" class=\"content\">2</text>\n" +
                "    <text x=\"64%\" y=\"52%\" class=\"content\">3</text>\n" +
                "    <text x=\"64%\" y=\"62%\" class=\"content\">4</text>\n" +
                "    <text x=\"64%\" y=\"72%\" class=\"content\">5</text>\n" +
                "    <text x=\"64%\" y=\"82%\" class=\"content\">6</text>\n" +
                "\n" +
                "  </svg>");
    }

    @DisplayName("스탯 카드에 유저 이름을 가지고 정보 넣기 성공")
    @Test
    void makeStackCardWithUserNameSucceed() throws FileNotFoundException {
        // given
        doReturn(Optional.of(users)).when(usersRepository).findByName(any(String.class));
        doReturn(1).when(feedbackRepository).countByUsers(any(Users.class));
        doReturn(2).when(reviewsRepository).countByUsers(any(Users.class));
        doReturn(3).when(selectedFeedbacksRepository).countByUsers(any(Users.class));
        doReturn(4).when(selectedReviewsRepository).countByUsers(any(Users.class));
        doReturn(5).when(projectsRepository).countByProjectWriter(any(Users.class));
        doReturn(6).when(codesRepository).countByCodeWriter(any(Users.class));

        // when
        String completeCard = mypageService.makeStatCardWithUserName(users.getName());

        // then
        assertThat(completeCard).isEqualTo("<svg viewBox=\"0 0 1030 445\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "    <style>\n" +
                "      .small {\n" +
                "        font: italic 13px sans-serif;\n" +
                "      }\n" +
                "      .heavy {\n" +
                "        font: bold 30px sans-serif;\n" +
                "      }\n" +
                "  \n" +
                "      /* Note that the color of the text is set with the    *\n" +
                "       * fill property, the color property is for HTML only */\n" +
                "      .Rrrrr {\n" +
                "        font: italic 40px serif;\n" +
                "        fill: red;\n" +
                "      }\n" +
                "      .title {\n" +
                "        font: 800 30px 'Segoe UI', Ubuntu, Sans-Serif;\n" +
                "        fill: #319795;\n" +
                "        animation: fadeInAnimation 0.8s ease-in-out forwards;\n" +
                "      }\n" +
                "      .content {\n" +
                "        font: 800 20px 'Segoe UI', Ubuntu, Sans-Serif;\n" +
                "        fill: #45474F;\n" +
                "        animation: fadeInAnimation 0.8s ease-in-out forwards;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <rect\n" +
                "        data-testid=\"card-bg\"\n" +
                "        x=\"1%\"\n" +
                "        y=\"0.5%\"\n" +
                "        rx=\"25\"\n" +
                "        height=\"99%\"\n" +
                "        stroke=\"#319795\"\n" +
                "        width=\"98%\"\n" +
                "        fill=\"#ffffff\"\n" +
                "      />\n" +
                "\n" +
                "      <rect\n" +
                "      data-testid=\"card-bg\"\n" +
                "      x=\"7%\"\n" +
                "      y=\"20%\"\n" +
                "      rx=\"5\"\n" +
                "      height=\"70%\"\n" +
                "      width=\"86%\"\n" +
                "      fill=\"#EFF8FF\"\n" +
                "    /> \n" +
                "  \n" +
                "    <text x=\"7%\" y=\"13%\" class=\"title\">${title}</text>\n" +
                "\n" +
                "    <text x=\"14%\" y=\"32%\" class=\"content\">1. 프로젝트 피드백 횟수:</text>\n" +
                "    <text x=\"14%\" y=\"42%\" class=\"content\">2. 코드 리뷰 횟수:</text>\n" +
                "    <text x=\"14%\" y=\"52%\" class=\"content\">3. 반영된 프로젝트 피드백 수:</text>\n" +
                "    <text x=\"14%\" y=\"62%\" class=\"content\">4. 반영된 코드 리뷰 수:</text>\n" +
                "    <text x=\"14%\" y=\"72%\" class=\"content\">5. 프로젝트 리팩토링 횟수:</text>\n" +
                "    <text x=\"14%\" y=\"82%\" class=\"content\">6. 코드 리팩토링 횟수:</text>\n" +
                "\n" +
                "    <text x=\"64%\" y=\"32%\" class=\"content\">1</text>\n" +
                "    <text x=\"64%\" y=\"42%\" class=\"content\">2</text>\n" +
                "    <text x=\"64%\" y=\"52%\" class=\"content\">3</text>\n" +
                "    <text x=\"64%\" y=\"62%\" class=\"content\">4</text>\n" +
                "    <text x=\"64%\" y=\"72%\" class=\"content\">5</text>\n" +
                "    <text x=\"64%\" y=\"82%\" class=\"content\">6</text>\n" +
                "\n" +
                "  </svg>");
    }

    @DisplayName("즐겨찾기한 코드 목록 조회 성공")
    @Test
    void getFavoriteCodeListSucceed(){
        // given
        List<CodeTags> tagsList = codeTagsInit();
        List<CodeSelectedTags> selectedTagsList = List.of(CodeSelectedTags.builder()
                .selectedTagsId(2L)
                .tags(tagsList.get(1))
                .codes(code)
                .build());
        List<Codes> codesList = List.of(
                Codes.builder()
                        .codesId(1L)
                        .num(1L)
                        .version(1)
                        .title("title")
                        .codeWriter(users)
                        .selectedTags(selectedTagsList)
                        .build()
        );

        int page = 0;


        List<CodeInfoRes> codetInfoRes = List.of(
                CodeInfoRes.builder()
                        .codeId(codesList.get(0).getCodesId())
                        .version(codesList.get(0).getVersion())
                        .title(codesList.get(0).getTitle())
                        .date(codesList.get(0).getModifiedDate())
                        .likeCnt(codesList.get(0).getLikeCnt())
                        .reviewCnt(codesList.get(0).getReviewCnt())
                        .tags(List.of("2"))
                        .userName(codesList.get(0).getCodeWriter().getName())
                        .liked(false)
                        .build()
        );

        PageRequest pageRequest = PageRequest.of(page, 10);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), codesList.size());
        final Page<Codes> codesPage = new PageImpl<>(codesList.subList(start, end), pageRequest, codesList.size());
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(codesPage).when(codesRepository).findAllMyFavorite(users.getUsersId(), pageRequest);

        // whecn
        Pair<List<CodeInfoRes>, Boolean> result = mypageService.getFavoriteCodeList(pageRequest, users.getUsersId());

        // then
        assertThat(result.getLeft().get(0).getCodeId()).isEqualTo(1L);
        assertThat(result.getRight()).isFalse();
    }

    @DisplayName("리뷰 남긴 코드 목록 조회 성공")
    @Test
    void getReviewCodeListSucceed(){
        // given
        List<CodeTags> tagsList = codeTagsInit();
        List<CodeSelectedTags> selectedTagsList = List.of(CodeSelectedTags.builder()
                .selectedTagsId(2L)
                .tags(tagsList.get(1))
                .codes(code)
                .build());
        List<Codes> codesList = List.of(
                Codes.builder()
                        .codesId(1L)
                        .num(1L)
                        .version(1)
                        .title("title")
                        .codeWriter(users)
                        .selectedTags(selectedTagsList)
                        .build()
        );

        int page = 0;


        List<CodeInfoRes> codetInfoRes = List.of(
                CodeInfoRes.builder()
                        .codeId(codesList.get(0).getCodesId())
                        .version(codesList.get(0).getVersion())
                        .title(codesList.get(0).getTitle())
                        .date(codesList.get(0).getModifiedDate())
                        .likeCnt(codesList.get(0).getLikeCnt())
                        .reviewCnt(codesList.get(0).getReviewCnt())
                        .tags(List.of("2"))
                        .userName(codesList.get(0).getCodeWriter().getName())
                        .liked(false)
                        .build()
        );

        PageRequest pageRequest = PageRequest.of(page, 10);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), codesList.size());
        final Page<Codes> codesPage = new PageImpl<>(codesList.subList(start, end), pageRequest, codesList.size());
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(codesPage).when(codesRepository).findAllMyReviews(users.getUsersId(), pageRequest);

        // whecn
        Pair<List<CodeInfoRes>, Boolean> result = mypageService.getReviewCodeList(pageRequest, users.getUsersId());

        // then
        assertThat(result.getLeft().get(0).getCodeId()).isEqualTo(1L);
        assertThat(result.getRight()).isFalse();
    }

    @DisplayName("내 코드 목록 조회 성공")
    @Test
    void getCodeListSucceed(){
        // given
        List<CodeTags> tagsList = codeTagsInit();
        List<CodeSelectedTags> selectedTagsList = List.of(CodeSelectedTags.builder()
                .selectedTagsId(2L)
                .tags(tagsList.get(1))
                .codes(code)
                .build());
        List<Codes> codesList = List.of(
                Codes.builder()
                        .codesId(1L)
                        .num(1L)
                        .version(1)
                        .title("title")
                        .codeWriter(users)
                        .selectedTags(selectedTagsList)
                        .build()
        );

        int page = 0;

        List<CodeInfoRes> codetInfoRes = List.of(
                CodeInfoRes.builder()
                        .codeId(codesList.get(0).getCodesId())
                        .version(codesList.get(0).getVersion())
                        .title(codesList.get(0).getTitle())
                        .date(codesList.get(0).getModifiedDate())
                        .likeCnt(codesList.get(0).getLikeCnt())
                        .reviewCnt(codesList.get(0).getReviewCnt())
                        .tags(List.of("2"))
                        .userName(codesList.get(0).getCodeWriter().getName())
                        .liked(false)
                        .build()
        );

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "modifiedDate"));
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), codesList.size());
        final Page<Codes> codesPage = new PageImpl<>(codesList.subList(start, end), pageRequest, codesList.size());
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(codesPage).when(codesRepository).findAllByCodeWriter(users.getUsersId(), pageRequest);

        // when
        Pair<List<CodeInfoRes>, Boolean> result = mypageService.getCodeList(pageRequest, users.getUsersId());

        // then
        assertThat(result.getLeft().get(0).getCodeId()).isEqualTo(1L);
        assertThat(result.getRight()).isFalse();
    }

    @DisplayName("피드백 남긴 프로젝트 목록 조회 성공")
    @Test
    void getFeedbackProjectListSucceed(){
        // given
        List<ProjectTags> tagsList = projectTagsInit();
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
        List<ProjectTags> tagsList = projectTagsInit();
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
        List<ProjectTags> tagsList = projectTagsInit();
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
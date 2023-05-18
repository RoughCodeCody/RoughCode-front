package com.cody.roughcode.code.service;

import com.cody.roughcode.alarm.dto.req.AlarmReq;
import com.cody.roughcode.alarm.service.AlarmServiceImpl;
import com.cody.roughcode.code.dto.req.CodeReq;
import com.cody.roughcode.code.dto.res.CodeDetailRes;
import com.cody.roughcode.code.dto.res.CodeTagsRes;
import com.cody.roughcode.code.dto.res.ReviewInfoRes;
import com.cody.roughcode.code.dto.res.ReviewSearchRes;
import com.cody.roughcode.code.entity.*;
import com.cody.roughcode.code.repository.*;
import com.cody.roughcode.email.service.EmailServiceImpl;
import com.cody.roughcode.exception.NotMatchException;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.repository.ProjectsRepository;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class) // 가짜 객체 주입을 사용
class CodesServiceTest {

    @InjectMocks
    private CodesServiceImpl codesService;
    @Mock
    private AlarmServiceImpl alarmService;
    @Mock
    private EmailServiceImpl emailService;

    @Mock
    private CodesRepository codesRepository;
    @Mock
    private CodesInfoRepository codesInfoRepository;
    @Mock
    private CodeTagsRepository codeTagsRepository;
    @Mock
    private CodeSelectedTagsRepository codeSelectedTagsRepository;
    @Mock
    private CodeFavoritesRepository codeFavoritesRepository;
    @Mock
    private CodeLikesRepository codeLikesRepository;
    @Mock
    private ProjectsRepository projectsRepository;
    @Mock
    private ReviewsRepository reviewsRepository;
    @Mock
    private ReviewLikesRepository reviewLikesRepository;
    @Mock
    private ReReviewsRepository reReviewsRepository;
    @Mock
    private ReReviewLikesRepository reReviewLikesRepository;
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
            .likeCnt(3)
            .build();

    final CodesInfo info = CodesInfo.builder()
            .githubUrl("https://api.github.com/repos/cody/hello-world/contents/src/main.py")
            .content("시간초과 뜹니다")
            .favoriteCnt(3)
            .build();

    private List<CodeTags> tagsInit() {
        List<CodeTags> tagsList = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            tagsList.add(CodeTags.builder()
                    .tagsId(i)
                    .name("tag" + i.toString())
                    .build());
        }
        return tagsList;
    }

    private List<CodeSelectedTags> selectedTagsList() {
        List<CodeTags> tags = tagsInit();
        List<CodeSelectedTags> tagsList = new ArrayList<>();
        for (Long i = 1L; i <= 3L; i++) {
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

    final AlarmReq alarmReq = AlarmReq.builder()
            .content(List.of("알람알람"))
            .userId(1L)
            .section("code")
            .postId(1L)
            .build();

    @DisplayName("코드 등록 성공 - 새 코드")
    @Test
    void insertCodeSucceed() throws MessagingException {
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
        doReturn(info).when(codesInfoRepository).save(any(CodesInfo.class));
        doReturn(project).when(projectsRepository).findByProjectsIdAndExpireDateIsNull(any(Long.class));

        // when
        Long successCodeId = codesService.insertCode(req, 1L);

        // then
        assertThat(successCodeId).isEqualTo(1L);
    }

    @DisplayName("코드 등록 성공 - 기존 코드 업데이트")
    @Test
    void insertCodeSucceedVersionUp() throws MessagingException {
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
                .lineNumbers("1,3,4,5")
                .codeContent("리뷰리뷰")
                .content("최소힙을 사용해보세요.")
                .build();
        SelectedReviews selectedReviews = SelectedReviews.builder()
                .selectedReviewsId(1L)
                .reviews(review)
                .codesInfo(info)
                .build();
        Codes updatedCode = Codes.builder()
                .codesId(2L)
                .num(1L)
                .version(2)
                .title("개발새발 코드2")
                .codeWriter(user)
                .build();

        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(updatedCode).when(codesRepository).save(any(Codes.class));
        doReturn(code).when(codesRepository).findLatestByCodesIdAndUsersId(any(Long.class), any(Long.class));
        doReturn(info).when(codesInfoRepository).save(any(CodesInfo.class));
        doReturn(project).when(projectsRepository).findByProjectsIdAndExpireDateIsNull(any(Long.class));
        alarmService.insertAlarm(any(AlarmReq.class));
        emailService.sendAlarm("이메일 전송", alarmReq);

        // when
        Long successCodeId = codesService.insertCode(req, 1L);

        // then
        assertThat(successCodeId).isEqualTo(2L);
    }

    @DisplayName("코드 등록 실패 - 존재하지 않는 유저 아이디")
    @Test
    void insertCodeFailNoUser() {
        // given
        CodeReq req = CodeReq.builder()
                .codeId(1L)
                .title("개발새발 코드")
                .selectedTagsId(List.of(1L))
                .githubUrl("https://api.github.com/repos/cody/hello-world/contents/src/main.py")
                .content("시간초과 뜹니다")
                .projectId(1L)
                .build();

        // when & then
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> codesService.insertCode(req, 1L)
        );

        assertEquals("일치하는 유저가 존재하지 않습니다", exception.getMessage());
    }

    @DisplayName("코드 등록 실패 - code 작성한 user랑 version up 하려는 user가 다름")
    @Test
    void insertCodeFailUserDiffer() {
        // given
        CodeReq req = CodeReq.builder()
                .codeId(1L)
                .title("개발새발 코드")
                .selectedTagsId(List.of(1L))
                .githubUrl("https://api.github.com/repos/cody/hello-world/contents/src/main.py")
                .content("시간초과 뜹니다")
                .projectId(1L)
                .build();

        Codes original = Codes.builder()
                .codesId(1L)
                .num(1L)
                .version(1)
                .title("개발새발 코드")
                .codeWriter(user2)
                .build();

        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(original).when(codesRepository).findLatestByCodesIdAndUsersId(any(Long.class), any(Long.class));

        // when & then
        NotMatchException exception = assertThrows(
                NotMatchException.class, () -> codesService.insertCode(req, 1L)
        );

        assertEquals("접근 권한이 없습니다", exception.getMessage());
    }

    @DisplayName("코드 상세 조회 성공")
    @Test
    void getCodeSucceed() {
        // given
        Long codeId = 1L;
        Codes code = Codes.builder()
                .codesId(1L)
                .num(1L)
                .version(1)
                .title("개발새발 코드")
                .codeWriter(user)
                .projects(project)
                .build();
        Codes code2 = Codes.builder()
                .codesId(2L)
                .num(2L)
                .version(2)
                .title("개발새발 코드2")
                .codeWriter(user)
                .projects(project)
                .build();
        CodesInfo info = CodesInfo.builder()
                .githubUrl("github url")
                .content("시간초과 떠요")
                .favoriteCnt(1)
                .build();
        CodeFavorites favorite = CodeFavorites.builder()
                .favoritesId(1L)
                .codes(code)
                .users(user)
                .build();
        CodeLikes like = CodeLikes.builder()
                .likesId(1L)
                .codes(code)
                .users(user)
                .build();

        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(code).when(codesRepository).findByCodesId(any(Long.class));
        doReturn(info).when(codesInfoRepository).findByCodes(any(Codes.class));
        doReturn(favorite).when(codeFavoritesRepository).findByCodesAndUsers(any(Codes.class), any(Users.class));
        doReturn(like).when(codeLikesRepository).findByCodesAndUsers(any(Codes.class), any(Users.class));
        doReturn(List.of(code, code2)).when(codesRepository).findByNumAndCodeWriterAndExpireDateIsNullOrderByVersionDesc(any(Long.class), any(Users.class));

        // when
        CodeDetailRes success = codesService.getCode(codeId, 1L);

        // then
        assertThat(success.getCodeId()).isEqualTo(1L);
        assertThat(success.getVersions().size()).isEqualTo(2);
    }

    @DisplayName("코드 수정 성공")
    @Test
    void updateCodeSucceed() {
        // given
        List<CodeTags> tagsList = tagsInit();
        CodeReq req = CodeReq.builder()
                .codeId(1L)
                .title("개발새발 코드")
                .selectedTagsId(List.of(1L))
                .githubUrl("https://api.github.com/repos/cody/hello-world/contents/src/main.py")
                .language(List.of(1L, 2L))
                .content("시간초과 뜹니다")
                .projectId(1L)
                .selectedTagsId(List.of(1L, 2L))
                .selectedReviewsId(List.of(1L, 2L))
                .build();
        CodeSelectedTags selectedTags = CodeSelectedTags.builder()
                .tags(tagsList.get(0))
                .codes(code)
                .build();
        Reviews review = Reviews.builder()
                .reviewsId(1L)
                .users(user)
                .codes(code)
                .lineNumbers("1,3,4,5")
                .codeContent("리뷰리뷰")
                .content("최소힙을 사용해보세요.")
                .build();
        SelectedReviews selectedReviews = SelectedReviews.builder()
                .selectedReviewsId(1L)
                .reviews(review)
                .codesInfo(info)
                .build();

        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(code).when(codesRepository).findByCodesIdAndExpireDateIsNull(any(Long.class));
        doReturn(info).when(codesInfoRepository).findByCodes(any(Codes.class));
        doReturn(tagsList).when(codeTagsRepository).findByTagsIdIn(Mockito.<Long>anyList());
        doReturn(List.of(review)).when(reviewsRepository).findByReviewsIdIn(Mockito.<Long>anyList());
        doReturn(project).when(projectsRepository).findByProjectsIdAndExpireDateIsNull(any(Long.class));

        // when
        int res = codesService.updateCode(req, 1L, 1L);

        // then
        assertThat(res).isEqualTo(1);
    }

    @DisplayName("코드 수정 실패 - 코드 작성자와 수정을 시도하는 사용자가 다름")
    @Test
    void updateCodeFailUserDiffer() {
        // given
        CodeReq req2 = CodeReq.builder()
                .codeId(1L)
                .title("개발새발 코드2")
                .selectedTagsId(List.of(1L, 2L))
                .githubUrl("https://api.github.com/repos/cody/hello-world/contents/src/main.py")
                .content("시간초과 뜹니다 22")
                .projectId(1L)
                .selectedTagsId(List.of(1L, 3L))
                .selectedReviewsId(List.of(3L, 4L))
                .build();
        Codes original = Codes.builder()
                .codesId(1L)
                .num(1L)
                .version(1)
                .title("개발새발 코드")
                .codeWriter(user2)
                .build();

        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(original).when(codesRepository).findByCodesIdAndExpireDateIsNull(any(Long.class));

        // when & then
        NotMatchException exception = assertThrows(
                NotMatchException.class, () -> codesService.updateCode(req2, 1L, 1L)
        );

        assertEquals("접근 권한이 없습니다", exception.getMessage());
    }

    @DisplayName("코드 삭제 성공")
    @Test
    void deleteCodeSucceed() {
        Reviews reviews = Reviews.builder()
                .reviewsId(1L)
                .codeContent("!212")
                .content("설명설명")
                .build();
        ReReviews reReviews = ReReviews.builder()
                .reviews(reviews)
                .content("리리뷰")
                .build();
        ReReviewLikes reReviewLikes = ReReviewLikes.builder()
                .likesId(1L)
                .reReviews(reReviews)
                .users(user).build();

        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(code).when(codesRepository).findByCodesIdAndExpireDateIsNull(any(Long.class));
        reReviewsRepository.deleteAllByCodesId(any(Long.class));
        reviewsRepository.deleteAllByCodesId(any(Long.class));
        reviewLikesRepository.deleteAllByCodesId(any(Long.class));
        reviewsRepository.deleteAllByCodesId(any(Long.class));
        codeLikesRepository.deleteAllByCodesId(any(Long.class));
        codeFavoritesRepository.deleteAllByCodesId(any(Long.class));

        // when
        int res = codesService.putExpireDateCode(1L, 1L);

        // then
        assertThat(res).isEqualTo(1);
    }

    @DisplayName("코드 삭제 실패 - 코드 작성자와 삭제를 시도하는 사용자가 다름")
    @Test
    void deleteCodeFailUserDiffer() {
        // given
        doReturn(user2).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(code).when(codesRepository).findByCodesIdAndExpireDateIsNull(any(Long.class));

        // when & then
        NotMatchException exception = assertThrows(
                NotMatchException.class, () -> codesService.putExpireDateCode(1L, 1L)
        );

        assertEquals("접근 권한이 없습니다", exception.getMessage());
    }

    @DisplayName("코드 좋아요 등록 성공")
    @Test
    void likeCodeSucceed() {
        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(code).when(codesRepository).findByCodesIdAndExpireDateIsNull(any(Long.class));
        doReturn(null).when(codeLikesRepository).findByCodesAndUsers(any(Codes.class), any(Users.class));

        int likeCnt = code.getLikeCnt();

        // when
        int res = codesService.likeCode(1L, 1L);

        // then
        assertThat(res).isEqualTo(code.getLikeCnt());
        assertThat(res).isEqualTo(likeCnt + 1);
    }

    @DisplayName("코드 좋아요 취소 성공")
    @Test
    void likeCodeCancelSucceed() {
        CodeLikes codeLikes = CodeLikes.builder()
                .codes(code)
                .users(user).build();

        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(code).when(codesRepository).findByCodesIdAndExpireDateIsNull(any(Long.class));
        doReturn(codeLikes).when(codeLikesRepository).findByCodesAndUsers(any(Codes.class), any(Users.class));

        int likeCnt = code.getLikeCnt();

        // when
        int res = codesService.likeCode(1L, 1L);

        // then
        assertThat(res).isEqualTo(code.getLikeCnt());
        assertThat(res).isEqualTo(likeCnt - 1);
    }

    @DisplayName("코드 좋아요 등록 또는 취소 실패 - 일치하는 유저가 없음")
    @Test
    void likeCodeFailNotFoundUser() {
        // given
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> codesService.likeCode(1L, 0L)
        );

        assertEquals("일치하는 유저가 없습니다", exception.getMessage());
    }

    @DisplayName("코드 좋아요 등록 또는 취소 실패 - 일치하는 코드가 없음")
    @Test
    void likeCodeFailNotFoundCode() {
        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(null).when(codesRepository).findByCodesIdAndExpireDateIsNull(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> codesService.likeCode(0L, 1L)
        );

        assertEquals("일치하는 코드가 없습니다", exception.getMessage());
    }

    @DisplayName("코드 즐겨찾기 등록 성공")
    @Test
    void favoriteCodeSucceed() {
        String content = "오.. DP를 활용한 효율적인 코드";

        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(info).when(codesInfoRepository).findByCodesId(any(Long.class));
        doReturn(null).when(codeFavoritesRepository).findByCodesIdAndUsersId(any(Long.class), any(Long.class));

        int favoriteCnt = info.getFavoriteCnt();

        // when
        int res = codesService.favoriteCode(1L, content, 1L);

        // then
        assertThat(res).isEqualTo(info.getFavoriteCnt());
        assertThat(res).isEqualTo(favoriteCnt + 1);
    }

    @DisplayName("코드 즐겨찾기 취소 성공")
    @Test
    void favoriteCodeCancelSucceed() {
        String content = "오.. DP를 활용한 효율적인 코드";

        CodeFavorites codeFavorites = CodeFavorites.builder()
                .content(content)
                .codes(code)
                .users(user).build();

        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(info).when(codesInfoRepository).findByCodesId(any(Long.class));
        doReturn(codeFavorites).when(codeFavoritesRepository).findByCodesIdAndUsersId(any(Long.class), any(Long.class));

        int favoriteCnt = info.getFavoriteCnt();

        // when
        int res = codesService.favoriteCode(1L, null, 1L);

        // then
        assertThat(res).isEqualTo(info.getFavoriteCnt());
        assertThat(res).isEqualTo(favoriteCnt - 1);
    }

    @DisplayName("코드 즐겨찾기 등록 또는 취소 실패 - 일치하는 유저가 없음")
    @Test
    void favoriteCodeFailNotFoundUser() {
        String content = "오.. DP를 활용한 효율적인 코드";

        // given
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> codesService.favoriteCode(1L, content, 0L)
        );

        assertEquals("일치하는 유저가 없습니다", exception.getMessage());
    }

    @DisplayName("코드 즐겨찾기 등록 또는 취소 실패 - 일치하는 코드가 없음")
    @Test
    void favoriteCodeFailNotFoundCode() {
        String content = "오.. DP를 활용한 효율적인 코드";

        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
//        doReturn(null).when(codesRepository).findByCodesId(any(Long.class));

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> codesService.favoriteCode(1L, content, 0L)
        );

        assertEquals("일치하는 코드가 없습니다", exception.getMessage());
    }

    @DisplayName("tag 목록 검색 성공")
    @Test
    void searchTagsSucceed() {
        // given
        List<CodeTags> tags = tagsInit();
        doReturn(tags).when(codeTagsRepository).findAllByNameContaining("", Sort.by(Sort.Direction.ASC, "name"));

        // when
        List<CodeTagsRes> result = codesService.searchTags("");

        // then
        AssertionsForClassTypes.assertThat(result.size()).isEqualTo(10);
    }

    @DisplayName("코드 리뷰 목록 조회 성공 - 코드 등록/수정 시 사용")
    @Test
    void getReviewListSucceed() {
        Reviews review = Reviews.builder()
                .reviewsId(1L)
                .lineNumbers("[[1,2],[3,4]]")
                .codeContent("import sys ..")
                .content("설명설명")
                .codes(code)
                .users(user)
                .likeCnt(3)
                .complained(false)
                .build();

        Codes code = Codes.builder()
                .codesId(2L)
                .title("코드 좀 봐주세요")
                .codeWriter(user)
                .num(2L)
                .reviews(List.of(review))
                .build();

        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(code).when(codesRepository).findByCodesIdAndExpireDateIsNull(any(Long.class));
        doReturn(List.of(code)).when(codesRepository).findByNumAndCodeWriterAndExpireDateIsNullOrderByVersionDesc(any(Long.class), any(Users.class));

        // when
        List<ReviewInfoRes> result = codesService.getReviewList(1L, 1L);

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    @DisplayName("코드 리뷰 목록 조회 성공 - 코드 상세조회 시 사용")
    @Test
    void getReviewSearchListSucceed() {
        Reviews review = Reviews.builder()
                .reviewsId(1L)
                .lineNumbers("[[1,2],[3,4]]")
                .codeContent("import sys ..")
                .content("설명설명")
                .codes(code)
                .users(user)
                .likeCnt(3)
                .complained(false)
                .build();

        Codes code = Codes.builder()
                .codesId(2L)
                .title("코드 좀 봐주세요")
                .codeWriter(user)
                .num(2L)
                .reviews(List.of(review))
                .build();
        ReviewLikes reviewLike = ReviewLikes.builder()
                .likesId(1L)
                .reviews(review)
                .users(user)
                .build();

        // given
        doReturn(user).when(usersRepository).findByUsersId(any(Long.class));
        doReturn(code).when(codesRepository).findByCodesIdAndExpireDateIsNull(any(Long.class));
        doReturn(reviewLike).when(reviewLikesRepository).findByReviewsAndUsers(any(Reviews.class), any(Users.class));

        // when
        List<ReviewSearchRes> result = codesService.getReviewSearchList(1L, 1L, "설명");

        // then
        assertThat(result.size()).isEqualTo(1);
    }
}
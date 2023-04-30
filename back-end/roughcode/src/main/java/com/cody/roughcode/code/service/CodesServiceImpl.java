package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.CodeReq;
import com.cody.roughcode.code.dto.res.*;
import com.cody.roughcode.code.entity.*;
import com.cody.roughcode.code.repository.*;
import com.cody.roughcode.exception.NotMatchException;
import com.cody.roughcode.exception.SaveFailedException;
import com.cody.roughcode.project.entity.CodeFavorites;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.repository.ProjectsRepository;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodesServiceImpl implements CodesService {

    private final UsersRepository usersRepository;
    private final CodesRepository codesRepository;
    private final CodesInfoRepository codesInfoRepository;
    private final CodeTagsRepository codeTagsRepository;
    private final CodeSelectedTagsRepository codeSelectedTagsRepository;
    private final ProjectsRepository projectsRepository;
    private final ReviewsRepository reviewsRepository;
    private final SelectedReviewsRepository selectedReviewsRepository;
    private final CodeFavoritesRepository codeFavoritesRepository;
    private final CodeLikesRepository codeLikesRepository;
    private final ReviewLikesRepository reviewLikesRepository;
    private final ReReviewsRepository reReviewsRepository;
    private final ReReviewLikesRepository reReviewLikesRepository;

    @Override
    @Transactional
    public List<CodeInfoRes> getCodeList(String sort, org.springframework.data.domain.PageRequest pageRequest,
                                         String keyword, String tagIds, Long userId) {
        Users user = usersRepository.findByUsersId(userId);
        if(user==null) {
            user = Users.builder().usersId(0L).build(); // 익명
        }
        List<Long> tagIdList = null;
        if(tagIds.length()>0){
            tagIdList = Arrays.stream(tagIds.split(","))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
        }

        if(keyword == null) {
            keyword = "";
        }

        Page<Codes> codesPage = null;
        if(tagIdList == null || tagIdList.size() == 0) {
            codesPage = codesRepository.findAllByKeyword(keyword, pageRequest);
        } else {
            codesPage = codeSelectedTagsRepository.findAllByKeywordAndTag(keyword, tagIdList, (long) tagIdList.size(), pageRequest);
        }

        return getCodeInfoRes(codesPage, user);
    }


    @Override
    @Transactional
    public Long insertCode(CodeReq req, Long userId) {

        Users user = usersRepository.findByUsersId(userId);
        if (user == null) {
            throw new NullPointerException("일치하는 유저가 존재하지 않습니다");
        }
        // github URL이 존재하지 않는다면 error
        if (req.getGithubUrl() == null) {
            throw new NullPointerException("코드를 불러올 github URL이 존재하지 않습니다");
        }

        // 새 코드를 생성하는 경우 codeNum은 작성자의 codes_cnt+1
        // 이전 코드를 업데이트 하는 경우 codeNum은 이전 code의 codeNum + 1
        Long codeNum;
        int codeVersion;

        if (req.getCodeId() == -1) { // 새 코드 등록
            user.codesCntUp(); // 사용자의 코드 등록 수 증가
            usersRepository.save(user); // 사용자 업데이트 정보 저장

            codeNum = user.getCodesCnt();
            codeVersion = 1;
        } else { // 기존 코드 업데이트
            Codes original = codesRepository.findLatestByCodesId(req.getCodeId());
            log.info("기존 코드 최근 버전: " + original.getVersion());
            if (original == null) {
                throw new NullPointerException("일치하는 코드가 없습니다.");
            }
            if (!original.getCodeWriter().equals(user)) {
                throw new NotMatchException();
            }

            codeNum = original.getNum();
            codeVersion = original.getVersion() + 1;
        }

        // 연결한 프로젝트가 있다면
        Projects connectedProject = null;
        if (req.getProjectId() != null) {
            connectedProject = projectsRepository.findByProjectsId(req.getProjectId());
        }

        Long codeId = -1L;
        try {
            // 코드 저장
            Codes code = Codes.builder()
                    .num(codeNum)
                    .version(codeVersion)
                    .title(req.getTitle())
                    .codeWriter(user)
                    .projects(connectedProject)
                    .build();
            Codes savedCode = codesRepository.save(code);
            codeId = savedCode.getCodesId();

            // tag 등록
            if (req.getSelectedTagsId() != null) {
                for (Long id : req.getSelectedTagsId()) {
                    CodeTags codeTag = codeTagsRepository.findByTagsId(id);
                    codeSelectedTagsRepository.save(
                            CodeSelectedTags.builder()
                                    .tags(codeTag)
                                    .codes(savedCode)
                                    .build());
                    // 태그 사용 수 증가
                    codeTag.cntUp();
                    codeTagsRepository.save(codeTag);
                }
            } else {
                log.info("등록한 태그가 없습니다.");
            }

            // 반영한 review 저장
            if (req.getSelectedReviewsId() != null) {
                for (Long id : req.getSelectedReviewsId()) {
                    Reviews review = reviewsRepository.findByReviewsId(id);
                    if (review == null) {
                        throw new NullPointerException("일치하는 리뷰가 없습니다.");
                    }
                    if (!review.getCodes().getNum().equals(codeNum)) {
                        throw new NullPointerException("리뷰와 프로젝트가 일치하지 않습니다.");
                    }
                    review.selectedUp();
                    reviewsRepository.save(review);

                    SelectedReviews selectedReviews = SelectedReviews.builder()
                            .reviews(review)
                            .codes(savedCode)
                            .build();
                    selectedReviewsRepository.save(selectedReviews);
                }
            } else {
                log.info("반영한 리뷰가 없습니다.");
            }

            // 코드 정보 저장
            CodesInfo codesInfo = CodesInfo.builder()
                    .codes(savedCode)
                    .githubUrl(req.getGithubUrl())
                    .content(req.getContent())
                    .favoriteCnt(0).build();
            codesInfoRepository.save(codesInfo);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SaveFailedException(e.getMessage());
        }

        return codeId;
    }

    @Override
    @Transactional
    public CodeDetailRes getCode(Long codeId, Long userId) {

        Users user = usersRepository.findByUsersId(userId);
        Codes code = codesRepository.findByCodesId(codeId);

        if (code == null) {
            throw new NullPointerException("일치하는 코드가 존재하지 않습니다");
        }
        CodesInfo codesInfo = codesInfoRepository.findByCodes(code);
        if (codesInfo == null) {
            throw new NullPointerException("일치하는 코드가 존재하지 않습니다");
        }

        // 코드에 등록된 태그 목록
        List<String> tagList = getTagNames(code);

        // 내가 즐겨찾기/좋아요 눌렀는지 여부
        CodeFavorites myFavorite = (user != null) ? codeFavoritesRepository.findByCodesAndUsers(code, user) : null;
        CodeLikes myLike = (user != null) ? codeLikesRepository.findByCodesAndUsers(code, user) : null;
        Boolean favorite = myFavorite != null;
        Boolean liked = myLike != null;

        // 연결된 프로젝트 정보(id, 제목) 저장
        Long connectedProjectId = null;
        String connectedProjectTitle = null;
        if (code.getProjects() != null) {
            connectedProjectId = code.getProjects().getProjectsId();
            connectedProjectTitle = code.getProjects().getTitle();
        }

        // 모든 버전 정보 미리보기
        List<Pair<Codes, CodesInfo>> otherVersions = new ArrayList<>();
        List<Codes> codeList = codesRepository.findByNumAndCodeWriter(code.getNum(), code.getCodeWriter());

        for (Codes c : codeList) {
            otherVersions.add(Pair.of(c, codesInfoRepository.findByCodes(c)));
        }

        List<VersionRes> versionResList = new ArrayList<>();
        for (Pair<Codes, CodesInfo> c : otherVersions) {
            List<SelectedReviewRes> selectedReviewResList = new ArrayList<>();
            if(c.getRight().getSelectedReviews() != null)
                for (var selectedReview : c.getRight().getSelectedReviews()) {
                    selectedReviewResList.add(SelectedReviewRes.builder()
                            .reviewId(selectedReview.getReviews().getReviewsId())
                            .userName(selectedReview.getReviews().getUsers().getName())
                            .content(selectedReview.getReviews().getContent())
                            .build());
                }
            versionResList.add(VersionRes.builder()
                    .selectedReviews(selectedReviewResList)
                    .codeId(c.getLeft().getCodesId())
                    .version(c.getLeft().getVersion())
                    .build());
        }

        // 리뷰 목록
        // - 순서 : 1.반영된 리뷰, 2.내가 쓴 리뷰, 3.나머지
        List<ReviewRes> reviewResList = new ArrayList<>();
        if(codesInfo.getReviews() != null) {
            for(Reviews review : codesInfo.getReviews()){
                ReviewLikes reviewLike =  (user != null) ? reviewLikesRepository.findByReviewsAndUsers(review, user): null;
                Boolean reviewLiked = reviewLike != null;

                List<ReReviewRes> reReviewResList = new ArrayList<>();
                for(ReReviews reReview: review.getReReviews()){
                    ReReviewLikes reReviewLike =  (user != null) ? reReviewLikesRepository.findByReReviewsAndUsers(reReview, user): null;
                    Boolean reReviewLiked = reReviewLike != null;
                    reReviewResList.add(ReReviewRes.toDto(reReview, reReviewLiked));
                }
                reviewResList.add(ReviewRes.toDto(review, reviewLiked, reReviewResList));
            }
        }
        reviewResList.sort(Comparator.comparing(ReviewRes::getSelected).reversed()
                .thenComparing((r1, r2) -> {
                    if ((r1.getUserId() != null && r1.getUserId().equals(userId)) && (r2.getUserId() == null || !r2.getUserId().equals(userId))) {
                        return -1;
                    } else if ((r1.getUserId() == null || !r1.getUserId().equals(userId)) && (r2.getUserId() != null && r2.getUserId().equals(userId))) {
                        return 1;
                    } else {
                        return r2.getDate().compareTo(r1.getDate());
                    }
                }));

        // 코드 상세 정보 response dto
        CodeDetailRes codeDetailRes = CodeDetailRes.builder()
                .codeId(code.getCodesId())
                .title(code.getTitle())
                .version(code.getVersion())
                .date(code.getModifiedDate())
                .likeCnt(code.getLikeCnt())
                .reviewCnt(code.getReviewCnt())
                .favoriteCnt(codesInfo.getFavoriteCnt())
                .githubUrl(codesInfo.getGithubUrl())
                .tags(tagList)
                .userId(user.getUsersId())
                .userName(user.getName())
                .projectId(connectedProjectId)
                .projectTitle(connectedProjectTitle)
                .content(codesInfo.getContent())
                .liked(liked)
                .favorite(favorite)
                .versions(versionResList)
                .reviews(reviewResList)
                .build();

        return codeDetailRes;
    }

    private List<CodeInfoRes> getCodeInfoRes(Page<Codes> codesPage, Users user) {
        List<Codes> codeList = codesPage.getContent();
        List<CodeInfoRes> codeInfoRes = new ArrayList<>();
        for (Codes c : codeList) {
            List<String> tagList = getTagNames(c);

            // 내가 좋아요 눌렀는지 여부
            CodeLikes codeLikes = codeLikesRepository.findByCodesAndUsers(c, user);
            Boolean liked = codeLikes != null ? true: false;

            codeInfoRes.add(CodeInfoRes.builder()
                    .codeId(c.getCodesId())
                    .version(c.getVersion())
                    .title(c.getTitle())
                    .date(c.getModifiedDate())
                    .likeCnt(c.getLikeCnt())
                    .reviewCnt(c.getReviewCnt())
                    .tags(tagList)
                    .userName(c.getCodeWriter().getName())
                    .liked(liked)
                    .build()
            );
        }
        return codeInfoRes;
    }

    private static List<String> getTagNames(Codes code) {
        List<String> tagList = new ArrayList<>();
        if (code.getSelectedTags() != null)
            for (CodeSelectedTags selected : code.getSelectedTags()) {
                tagList.add(selected.getTags().getName());
            }
        return tagList;
    }
}

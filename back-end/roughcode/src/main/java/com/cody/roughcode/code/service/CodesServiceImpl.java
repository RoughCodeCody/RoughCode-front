package com.cody.roughcode.code.service;

import com.cody.roughcode.alarm.dto.req.AlarmReq;
import com.cody.roughcode.alarm.service.AlarmServiceImpl;
import com.cody.roughcode.code.dto.req.CodeReq;
import com.cody.roughcode.code.dto.res.*;
import com.cody.roughcode.code.entity.*;
import com.cody.roughcode.code.repository.*;
import com.cody.roughcode.email.service.EmailServiceImpl;
import com.cody.roughcode.exception.*;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodesServiceImpl implements CodesService {

    private final UsersRepository usersRepository;
    private final CodesRepository codesRepository;
    private final CodesInfoRepository codesInfoRepository;
    private final CodeTagsRepository codeTagsRepository;
    private final CodeLanguagesRepository codeLanguagesRepository;
    private final CodeSelectedTagsRepository codeSelectedTagsRepository;
    private final ProjectsRepository projectsRepository;
    private final ReviewsRepository reviewsRepository;
    private final SelectedReviewsRepository selectedReviewsRepository;
    private final CodeFavoritesRepository codeFavoritesRepository;
    private final CodeLikesRepository codeLikesRepository;
    private final ReviewLikesRepository reviewLikesRepository;
    private final ReReviewsRepository reReviewsRepository;
    private final ReReviewLikesRepository reReviewLikesRepository;

    private final AlarmServiceImpl alarmService;
    private final EmailServiceImpl emailService;

    private final EntityManager entityManager;

    @Override
    @Transactional
    public List<CodeInfoRes> getCodeList(String sort, PageRequest pageRequest,
                                         String keyword, String tagIds, Long userId) {
        Users user = usersRepository.findByUsersId(userId);
        if (user == null) {
            user = Users.builder().usersId(0L).build(); // 익명
        }
        List<Long> tagIdList = null;
        if (tagIds != null && tagIds.length() > 0) {
            tagIdList = Arrays.stream(tagIds.split(","))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
        }

        if (keyword == null) {
            keyword = "";
        }

        Page<Codes> codesPage = null;
        if (tagIdList == null || tagIdList.size() == 0) {
            codesPage = codesRepository.findAllByKeyword(keyword, pageRequest);
        } else {
//            codesPage = codeSelectedTagsRepository.findAllByKeywordAndTag(keyword, tagIdList, (long) tagIdList.size(), pageRequest); // 태그 필터링 (AND)
            codesPage = codesRepository.findAllByKeywordAndLanguage(keyword, tagIdList, pageRequest); // 언어 필터링 (OR)
        }

        return getCodeInfoRes(codesPage, user);
    }


    @Override
    @Transactional
    public Long insertCode(CodeReq req, Long userId) throws MessagingException {

        Users user = usersRepository.findByUsersId(userId);
        if (user == null) {
            throw new NullPointerException("일치하는 유저가 존재하지 않습니다");
        }

        // 알람을 보낼 userIds
        List<Long> bookmarkAlarm = new ArrayList<>(); // 기존 코드를 북마크한 사용자
        List<Long> reviewAlarm = new ArrayList<>(); // 등록하는 코드에 반영되는 리뷰 작성자

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
            Codes original = codesRepository.findLatestByCodesIdAndUsersId(req.getCodeId(), userId);
            if (original == null) {
                throw new NullPointerException("일치하는 코드가 존재하지 않습니다");
            }
            if (!original.getCodeWriter().equals(user)) {
                throw new NotMatchException();
            }

            log.info("기존 코드 최근 버전: " + original.getVersion());
            codeNum = original.getNum();
            codeVersion = original.getVersion() + 1;

            // 북마크한 사용자 목록 저장
            if (original.getCodeFavorites() != null) {
                for (CodeFavorites cf : original.getCodeFavorites()) {
                    bookmarkAlarm.add(cf.getUsers().getUsersId());
                }
            }
        }

        // 연결한 프로젝트가 있다면
        Projects connectedProject = null;
        if (req.getProjectId() != null) {
            connectedProject = projectsRepository.findByProjectsIdAndExpireDateIsNull(req.getProjectId());
        }

        Codes savedCode = null;
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
            savedCode = codesRepository.save(code);
            codeId = savedCode.getCodesId();

            // tag 등록
            if (req.getSelectedTagsId() != null) {
                List<CodeSelectedTags> selectedTagsList = new ArrayList<>();
                List<CodeTags> codeTags = codeTagsRepository.findByTagsIdIn(req.getSelectedTagsId());
                for (CodeTags codeTag : codeTags) {
                    selectedTagsList.add(
                            CodeSelectedTags.builder()
                                    .tags(codeTag)
                                    .codes(savedCode)
                                    .build());
                    // 태그 사용 수 증가
                    codeTag.cntUp();
                }
                codeSelectedTagsRepository.saveAll(selectedTagsList);
            } else {
                log.info("등록한 태그가 없습니다.");
            }

            log.info("코드 등록 : " + req.getLanguage());
            // 새로운 코드 언어 등록 (한개만 받음!)
            CodeLanguages newLanguage = codeLanguagesRepository.findByLanguagesId(req.getLanguage().get(0));
            log.info("코드 등록 : " + newLanguage.getName());
            newLanguage.cntUp();

            // 파싱한 github api url
//            String parsedGithubUrl = parseGithubLink(req.getGithubUrl());
            log.info("코드 등록 : " + req.getGithubUrl());
            // 역파싱한 github url
            String reverseParseGithubUrl = reverseParseGithubLink(req.getGithubUrl());
            log.info("github URL 파싱 전!!!! " + reverseParseGithubUrl);

            // 코드 정보 저장
            CodesInfo codesInfo = CodesInfo.builder()
                    .codes(savedCode)
                    .githubUrl(reverseParseGithubUrl)
                    .githubApiUrl(req.getGithubUrl())
                    .content(req.getContent())
                    .language(newLanguage)
                    .favoriteCnt(0).build();
            codesInfoRepository.save(codesInfo);

            // 반영한 review 저장
            if (req.getSelectedReviewsId() != null) {
                List<Reviews> reviewsList = reviewsRepository.findByReviewsIdIn(req.getSelectedReviewsId());
                List<SelectedReviews> selectedReviewsList = new ArrayList<>();
                for (Reviews review : reviewsList) {
                    if (review == null) {
                        throw new NullPointerException("일치하는 리뷰가 없습니다.");
                    }
                    if (!review.getCodes().getNum().equals(codeNum)) {
                        throw new NullPointerException("리뷰와 코드가 일치하지 않습니다.");
                    }
                    review.selectedUp();
                    reviewsList.add(review);

                    SelectedReviews selectedReviews = SelectedReviews.builder()
                            .reviews(review)
                            .codesInfo(codesInfo)
                            .build();
                    selectedReviewsList.add(selectedReviews);

                    // 반영한 리뷰 작성자 목록 저장
                    if (review.getUsers() != null) {
                        reviewAlarm.add(review.getUsers().getUsersId());
                    }
                }
                reviewsRepository.saveAll(reviewsList);
                selectedReviewsRepository.saveAll(selectedReviewsList);

            } else {
                log.info("반영한 리뷰가 없습니다.");
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SaveFailedException(e.getMessage());
        }

        // 알람 저장
        // 북마크한 무슨무슨 코드 ver1의 새 버전 ver2 업데이트  -> [“북마크한”, “무슨무슨 코드 ver1의 새 버전 ver2”, “업데이트”]
        for (Long id : bookmarkAlarm) {
            AlarmReq alarmContent = AlarmReq.builder()
                    .section("code")
                    .userId(id)
                    .content(List.of("북마크한", savedCode.getTitle() + " ver" + (savedCode.getVersion() - 1) + "의 새 버전 ver" + savedCode.getVersion(), "가 업데이트 되었습니다"))
                    .postId(codeId).build();
            alarmService.insertAlarm(alarmContent);

            emailService.sendAlarm("북마크한 코드가 업데이트되었습니다", alarmContent);
        }
        // 작성한 리뷰가 반영된 무슨무슨 코드 ver1의 새 버전 ver2 업데이트 -> [“작성한 리뷰가 반영된”, “무슨무슨 코드 ver1의 새 버전 ver2”, “업데이트”]
        for (Long id : reviewAlarm) {
            AlarmReq alarmContent = AlarmReq.builder()
                    .section("code")
                    .userId(id)
                    .content(List.of("작성한 리뷰가 반영된", savedCode.getTitle() + " ver" + (savedCode.getVersion() - 1) + "의 새 버전 ver" + savedCode.getVersion(), "가 업데이트 되었습니다"))
                    .postId(codeId).build();
            alarmService.insertAlarm(alarmContent);

            emailService.sendAlarm("리뷰가 반영되었습니다", alarmContent);
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
        if (code.getExpireDate() != null) {
            throw new NullPointerException("삭제 처리된 코드입니다");
        }
        CodesInfo codesInfo = codesInfoRepository.findByCodes(code);
        if (codesInfo == null) {
            throw new NullPointerException("일치하는 코드가 존재하지 않습니다");
        }

        // 내가 작성한 코드인지 여부
        Boolean mine = code.getCodeWriter().equals(user);

        // 코드에 등록된 태그 목록
        List<CodeTagsRes> tagList = getTags(code);

        // 내가 즐겨찾기/좋아요 눌렀는지 여부
        CodeFavorites myFavorite = (user != null) ? codeFavoritesRepository.findByCodesAndUsers(code, user) : null;
        CodeLikes myLike = (user != null) ? codeLikesRepository.findByCodesAndUsers(code, user) : null;
        Boolean favorite = myFavorite != null;
        Boolean liked = myLike != null;

        // 연결된 프로젝트 정보(id, 제목) 저장
        Long connectedProjectId = null;
        String connectedProjectTitle = null;
        log.info("연결된 프로젝트정보 : " + code.getProjects());
        if (code.getProjects() != null) {
            log.info("연결된 프로젝트정보 : " + code.getProjects().getProjectsId());
            connectedProjectId = code.getProjects().getProjectsId();
            connectedProjectTitle = code.getProjects().getTitle();
        }

        // 모든 버전 정보 미리보기
        List<Pair<Codes, CodesInfo>> otherVersions = new ArrayList<>();
        List<Codes> codeList = codesRepository.findByNumAndCodeWriterAndExpireDateIsNullOrderByVersionDesc(code.getNum(), code.getCodeWriter());

        // 최신 버전인지
        Boolean latest = codeList.get(0).getCodesId().equals(codeId);

        for (Codes c : codeList) {
            otherVersions.add(Pair.of(c, codesInfoRepository.findByCodes(c)));
        }

        List<VersionRes> versionResList = new ArrayList<>();
        for (Pair<Codes, CodesInfo> c : otherVersions) {
            List<SelectedReviewRes> selectedReviewResList = new ArrayList<>();
            if (c.getRight().getSelectedReviews() != null)
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
                    .title(c.getLeft().getTitle())
                    .version(c.getLeft().getVersion())
                    .build());
        }

        // 리뷰 목록
        // - 순서 : 1.반영된 리뷰, 2.내가 쓴 리뷰, 3.나머지
        List<ReviewRes> reviewResList = new ArrayList<>();
        if (code.getReviews() != null) {
            for (Reviews review : code.getReviews()) {
                ReviewLikes reviewLike = (user != null) ? reviewLikesRepository.findByReviewsAndUsers(review, user) : null;
                Boolean reviewLiked = reviewLike != null;

                reviewResList.add(ReviewRes.toDto(review, reviewLiked, null));
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

        // 첫번째 리뷰에 대한 리리뷰 정보 추가
        if (reviewResList.size() > 0) {
            List<ReReviewRes> reReviewResList = new ArrayList<>();
            Long reviewId = reviewResList.get(0).getReviewId();
            for (ReReviews reReview : reReviewsRepository.findAllByReviewsId(reviewId)) {
                ReReviewLikes reReviewLike = (user != null) ? reReviewLikesRepository.findByReReviewsAndUsers(reReview, user) : null;
                Boolean reReviewLiked = reReviewLike != null;
                reReviewResList.add(ReReviewRes.toDto(reReview, reReviewLiked));
            }
            // 1. 내가 쓴 리리뷰, 2. 최신순 으로 정렬
            Collections.sort(reReviewResList, (r1, r2) -> {
                if ((r1.getUserId() != null && r1.getUserId().equals(userId)) && (r2.getUserId() == null || !r2.getUserId().equals(userId))) {
                    return -1;
                } else if ((r1.getUserId() == null || !r1.getUserId().equals(userId)) && (r2.getUserId() != null && r2.getUserId().equals(userId))) {
                    return 1;
                } else {
                    return r2.getDate().compareTo(r1.getDate());
                }
            });

            reviewResList.get(0).updateReReviews(reReviewResList);
        }

        Long codeWriterId = null;
        String codeWriterName = null;
        if (code.getCodeWriter() != null) {
            codeWriterId = code.getCodeWriter().getUsersId();
            codeWriterName = code.getCodeWriter().getName();
        }

        // 코드 상세 정보 response dto
        return CodeDetailRes.builder()
                .codeId(code.getCodesId())
                .mine(mine)
                .title(code.getTitle())
                .version(code.getVersion())
                .date(code.getCreatedDate())
                .likeCnt(code.getLikeCnt())
                .reviewCnt(code.getReviewCnt())
                .favoriteCnt(codesInfo.getFavoriteCnt())
                .githubUrl(codesInfo.getGithubUrl())
                .githubApiUrl(codesInfo.getGithubApiUrl())
                .tags(tagList)
                .userId(codeWriterId)
                .userName(codeWriterName)
                .projectId(connectedProjectId)
                .projectTitle(connectedProjectTitle)
                .content(codesInfo.getContent())
                .liked(liked)
                .favorite(favorite)
                .language(new CodeLanguagesRes(codesInfo.getLanguage()))
                .latest(latest)
                .versions(versionResList)
                .reviews(reviewResList)
                .build();
    }

    @Override
    @Transactional
    public int updateCode(CodeReq req, Long codeId, Long userId) {
        Users user = usersRepository.findByUsersId(userId);

        // 코드 작성자 확인
        if (user == null) {
            throw new NullPointerException("일치하는 유저가 존재하지 않습니다");
        }

        // 기존 코드 가져오기
        Codes target = codesRepository.findByCodesIdAndExpireDateIsNull(codeId);
        if (target == null) {
            throw new NullPointerException("일치하는 코드가 존재하지 않습니다");
        }
        if (!target.getCodeWriter().equals(user)) {
            // 코드 작성자와 사용자가 일치하지 않는 경우
            throw new NotMatchException();
        }

        CodesInfo targetInfo = codesInfoRepository.findByCodes(target);
        if (targetInfo == null) {
            throw new NullPointerException("일치하는 코드가 존재하지 않습니다");
        }

        try {
            // 기존 Tag 삭제
            List<CodeSelectedTags> selectedTagsList = target.getSelectedTags();
            if (selectedTagsList != null) {
                List<CodeTags> tagsList = new ArrayList<>();
                for (CodeSelectedTags tag : selectedTagsList) {
                    CodeTags codeTag = tag.getTags();
                    codeTag.cntDown();
                    tagsList.add(codeTag);
                }
                codeTagsRepository.saveAll(tagsList);
                codeSelectedTagsRepository.deleteAll(selectedTagsList);
            } else {
                log.info("기존에 선택하였던 태그가 없습니다");
            }

            // 업데이트한 Tag 등록
            if (req.getSelectedTagsId() != null) {
                List<CodeSelectedTags> updatedSelectedTagsList = new ArrayList<>();
                List<CodeTags> codeTags = codeTagsRepository.findByTagsIdIn(req.getSelectedTagsId());
                for (CodeTags codeTag : codeTags) {
                    updatedSelectedTagsList.add(CodeSelectedTags.builder()
                            .tags(codeTag)
                            .codes(target)
                            .build());
                    codeTag.cntUp();
                }
                codeSelectedTagsRepository.saveAll(updatedSelectedTagsList);
            } else {
                log.info("새로 선택한 태그가 없습니다");
            }

            // 기존 코드 언어 제거
            targetInfo.getLanguage().cntDown();

            // 새로운 코드 언어 등록 (한개만 받음!)
            CodeLanguages newLanguage = codeLanguagesRepository.findByLanguagesId(req.getLanguage().get(0));
            targetInfo.updateLanguage(newLanguage);
            newLanguage.cntUp();

            // 기존에 선택한 review 삭제
            List<SelectedReviews> selectedReviewsList = targetInfo.getSelectedReviews();
            if (selectedReviewsList != null) {
                List<Reviews> reviewsList = new ArrayList<>();
                for (SelectedReviews review : selectedReviewsList) {
                    Reviews reviews = review.getReviews();
                    reviews.selectedDown();
                    reviewsList.add(reviews);
                }
                reviewsRepository.saveAll(reviewsList);
                selectedReviewsRepository.deleteAll(selectedReviewsList);
            } else {
                log.info("기존에 선택하였던 리뷰가 없습니다");
            }

            // 새로 선택한 review 등록
            if (req.getSelectedReviewsId() != null) {
                List<SelectedReviews> newSelectedReviewsList = new ArrayList<>();
                List<Reviews> reviews = reviewsRepository.findByReviewsIdIn(req.getSelectedReviewsId());
                for (Reviews review : reviews) {
                    newSelectedReviewsList.add(SelectedReviews.builder()
                            .codesInfo(targetInfo)
                            .reviews(review)
                            .build());

                    review.selectedUp();
                }
                selectedReviewsRepository.saveAll(newSelectedReviewsList);
            } else {
                log.info("새로 선택한 리뷰가 없습니다");
            }

            // 연결된 프로젝트
            Projects connectedProject = null;
            if (req.getProjectId() != null) {
                connectedProject = projectsRepository.findByProjectsIdAndExpireDateIsNull(req.getProjectId());

//                if (connectedProject == null) {
//                    throw new NullPointerException("일치하는 프로젝트가 없습니다");
//                }
            }

            // 코드 정보 업데이트
            if (StringUtils.hasText(req.getTitle())) {
                log.info("코드 정보 수정(제목): " + req.getTitle());
                target.updateTitle(req.getTitle());
            }

            if (StringUtils.hasText(req.getContent())) {
                log.info("코드 정보 수정(상세설명): " + req.getContent());
                targetInfo.updateContent(req.getContent());
            }

            if (StringUtils.hasText(req.getGithubUrl())) {
                log.info("코드 정보 수정(github URL): " + req.getGithubUrl());
                String updateReverseParsedUrl = reverseParseGithubLink(req.getGithubUrl());
                targetInfo.updateGithubUrl(updateReverseParsedUrl);
                targetInfo.updateGithubApiUrl(req.getGithubUrl());
            }

            target.setProject(connectedProject); // 코드와 연결된 프로젝트 수정

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UpdateFailedException(e.getMessage());
        }

        return 1;
    }

    @Override
    @Transactional
    public int putExpireDateCode(Long codeId, Long userId) {
        Users user = usersRepository.findByUsersId(userId);

        // 코드 작성자 확인
        if (user == null) {
            throw new NullPointerException("일치하는 유저가 존재하지 않습니다");
        }
        // 기존 코드 가져오기
        Codes target = codesRepository.findByCodesIdAndExpireDateIsNull(codeId);
        if (target == null) {
            throw new NullPointerException("일치하는 코드가 존재하지 않습니다");
        }
        if (!target.getCodeWriter().equals(user)) {
            // 코드 작성자와 사용자가 일치하지 않는 경우
            throw new NotMatchException();
        }

        // 삭제 날짜 설정 (30일 후)
        target.setExpireDate();

        log.info("버전 : " + target.getNum());
        // 삭제하는 코드 버전이 1이라면 User의 codeCnt값 1 감소
        if (target.getVersion() == 1) {
            user.codesCntDown();
        }

        return 1;

    }

    @Override
    @Transactional
    public void deleteExpiredCode() {
        LocalDateTime now = LocalDateTime.now();
        List<Codes> expiredCodes = codesRepository.findByExpireDateBefore(now);

        // 삭제될 코드가 없으면 함수 종료
        if (expiredCodes == null) {
            return;
        }

        for (Codes target : expiredCodes) {
            // 연결된 프로젝트에서 코드 제거
            if (target.getProjects() != null && target.getProjects().getProjectsCodes() != null) {
                Iterator<Codes> iterator = target.getProjects().getProjectsCodes().iterator();
                while (iterator.hasNext()) {
                    Codes c = iterator.next();
                    if (c.getCodesId().equals(target.getCodesId())) {
                        iterator.remove();
                    }
                }
            }
        }

        // 기존에 선택한 태그 삭제
        codeSelectedTagsRepository.deleteAllByCodesList(expiredCodes);

        // 기존에 선택한 리뷰 삭제
        selectedReviewsRepository.deleteAllByCodesList(expiredCodes);

        // 코드에 등록된 리뷰에 대한 리뷰 좋아요 삭제
        reReviewLikesRepository.deleteAllByCodesList(expiredCodes);

        // 코드에 등록된 리뷰에 대한 리뷰 목록 삭제
        reReviewsRepository.deleteAllByCodesList(expiredCodes);

        // 코드에 등록된 리뷰 좋아요 목록 삭제
        reviewLikesRepository.deleteAllByCodesList(expiredCodes);

        // 코드에 등록된 리뷰 삭제
        reviewsRepository.deleteAllByCodesList(expiredCodes);

        // 코드 좋아요 목록 삭제
        codeLikesRepository.deleteAllByCodesList(expiredCodes);

        // 코드 즐겨찾기 목록 삭제
        codeFavoritesRepository.deleteAllByCodesList(expiredCodes);

        // 코드 정보 삭제
        codesInfoRepository.deleteAllByCodesList(expiredCodes);

        // 코드 삭제
        codesRepository.deleteAll(expiredCodes);

        // 영속성 컨텍스트 초기화
        entityManager.clear();

    }


    @Override
    @Transactional
    public int likeCode(Long codeId, Long userId) {

        Users user = usersRepository.findByUsersId(userId);

        if (user == null) {
            throw new NullPointerException("일치하는 유저가 없습니다");
        }

        Codes target = codesRepository.findByCodesIdAndExpireDateIsNull(codeId);
        if (target == null) {
            throw new NullPointerException("일치하는 코드가 없습니다");
        }

        // 좋아요를 누른 여부 확인 (눌려있다면 취소 처리, 새로 누른 경우 등록 처리)
        CodeLikes codeLikes = codeLikesRepository.findByCodesAndUsers(target, user);

        // 좋아요가 눌려있다면 취소 처리
        if (codeLikes != null) {
            // CodeLikes 데이터 삭제
            codeLikesRepository.delete(codeLikes);
            // 코드 좋아요 수 감소
            target.likeCntDown();
        } else { // 새로 누른 경우 등록 처리
            // CodeLikes 데이터 추가
            codeLikesRepository.save(
                    CodeLikes.builder()
                            .codes(target)
                            .users(user)
                            .build());
            // 코드 좋아요 수 증가
            target.likeCntUp();
        }

        // 좋아요 수 반환
        return target.getLikeCnt();
    }

    @Override
    @Transactional
    public int favoriteCode(Long codeId, String content, Long userId) {

        Users user = usersRepository.findByUsersId(userId);

        if (user == null) {
            throw new NullPointerException("일치하는 유저가 없습니다");
        }

        CodesInfo target = codesInfoRepository.findByCodesId(codeId);
        if (target == null) {
            throw new NullPointerException("일치하는 코드가 없습니다");
        }

        // 즐겨찾기를 누른 여부 확인 (눌려있다면 취소 처리, 새로 누른 경우 등록 처리)
        CodeFavorites codeFavorites = codeFavoritesRepository.findByCodesIdAndUsersId(codeId, userId);

        // 즐겨찾기가 눌려있다면 취소 처리
        if (codeFavorites != null) {
            // CodeFavorites 데이터 삭제
            codeFavoritesRepository.delete(codeFavorites);
            // 코드 즐겨찾기 수 감소
            target.favoriteCntDown();
        } else { // 새로 누른 경우 등록 처리
            // CodeFavorites 데이터 추가
            codeFavoritesRepository.save(
                    CodeFavorites.builder()
                            .content(content)
                            .codes(target.getCodes())
                            .users(user)
                            .build());
            // 코드 즐겨찾기 수 증가
            target.favoriteCntUp();
        }

        // 즐겨찾기 수 반환
        return target.getFavoriteCnt();
    }

    @Override
    @Transactional
    public List<CodeTagsRes> searchTags(String keyword) {
        List<CodeTags> tags = codeTagsRepository.findAllByNameContaining(keyword, Sort.by(Sort.Direction.ASC, "name"));
        List<CodeTagsRes> result = new ArrayList<>();
        for (CodeTags tag : tags) {
            result.add(new CodeTagsRes(tag));
        }
        return result;
    }

    @Override
    @Transactional
    public List<ReviewInfoRes> getReviewList(Long codeId, Long userId, boolean versionUp) {
        Users user = usersRepository.findByUsersId(userId);
        if (user == null) {
            throw new NullPointerException("일치하는 유저가 존재하지 않습니다");
        }

        Codes code = codesRepository.findByCodesIdAndExpireDateIsNull(codeId);
        if (code == null) {
            throw new NullPointerException("일치하는 코드가 존재하지 않습니다");
        }

        // num이 일치하는 버전들의 모든 코드 리뷰 목록 조회
        List<Codes> allVersion = codesRepository.findByNumAndCodeWriterAndExpireDateIsNullOrderByVersionDesc(code.getNum(), user);

        int idx = (versionUp) ? 0 : 1;
        List<ReviewInfoRes> reviewInfoResList = new ArrayList<>();
        for (; idx < allVersion.size(); idx++) {
            Codes c = allVersion.get(idx);
            for (Reviews r : c.getReviews()) {
                // 삭제 처리된 코드 리뷰 제외
                if (r.getCodeContent() == null || r.getCodeContent().equals("")) {
                    continue;
                }
                // 신고 횟수가 5번 이상인 코드 리뷰 제외
                if (Boolean.TRUE.equals(r.getComplained())) {
                    continue;
                }

                reviewInfoResList.add(new ReviewInfoRes(r, c.getVersion(), r.getUsers()));
            }
        }

        return reviewInfoResList;
    }

    @Override
    @Transactional
    public List<ReviewSearchRes> getReviewSearchList(Long codeId, Long userId, String keyword) {
        Users user = usersRepository.findByUsersId(userId);

        Codes code = codesRepository.findByCodesIdAndExpireDateIsNull(codeId);
        if (code == null) {
            throw new NullPointerException("일치하는 코드가 존재하지 않습니다");
        }

        List<ReviewSearchRes> reviewSearchResList = new ArrayList<>();
        for (Reviews r : code.getReviews()) {
            // 삭제 처리된 코드 리뷰 제외
            if (r.getCodeContent() == null || r.getCodeContent().equals("")) {
                continue;
            }
            // 신고 횟수가 5번 이상인 코드 리뷰 제외
            if (Boolean.TRUE.equals(r.getComplained())) {
                continue;
            }

            // 검색 대상 : 코드 내용, 상세 설명, 닉네임
            if (!r.getCodeContent().contains(keyword) && !r.getContent().contains(keyword) && !r.getUsers().getName().contains(keyword)) {
                // keyword 포함하지 않는 경우 continue
                continue;
            }
            // 좋아요 여부
            Boolean liked = reviewLikesRepository.findByReviewsAndUsers(r, user) != null;

            reviewSearchResList.add(new ReviewSearchRes(r, liked));
        }
        Collections.sort(reviewSearchResList, (r1, r2) -> r2.getDate().compareTo(r1.getDate()));

        return reviewSearchResList;
    }

    @Override
    @Transactional
    public int connectProject(Long codeId, Long userId, Long projectId) {
        Users user = usersRepository.findByUsersId(userId);
        if (user == null) {
            throw new NullPointerException("일치하는 유저가 존재하지 않습니다");
        }

        Codes code = codesRepository.findByCodesIdAndExpireDateIsNull(codeId);
        if (code == null) {
            throw new NullPointerException("일치하는 코드가 존재하지 않습니다");
        }

        Projects project = null;
        if (projectId != -1L) {
            project = projectsRepository.findByProjectsIdAndExpireDateIsNull(projectId);
            if (project == null) {
                throw new NullPointerException("연결하려는 프로젝트가 존재하지 않습니다");
            }
        }

        // project 연결
        code.setProject(project);

        return 1;
    }

    @Override
    @Transactional
    public List<CodeLanguagesRes> searchLanguages(String keyword) {
        List<CodeLanguages> languages = codeLanguagesRepository.findAllByNameContaining(keyword, Sort.by(Sort.Direction.ASC, "name"));
        List<CodeLanguagesRes> result = new ArrayList<>();
        for (CodeLanguages language : languages) {
            result.add(new CodeLanguagesRes(language));
        }
        return result;
    }

    private List<CodeInfoRes> getCodeInfoRes(Page<Codes> codesPage, Users user) {
        List<Codes> codeList = codesPage.getContent();
        List<CodeInfoRes> codeInfoRes = new ArrayList<>();
        for (Codes c : codeList) {
            List<CodeTagsRes> tagList = getTags(c);

            // 내가 좋아요 눌렀는지 여부
            CodeLikes codeLikes = codeLikesRepository.findByCodesAndUsers(c, user);
            Boolean liked = codeLikes != null;

            codeInfoRes.add(CodeInfoRes.builder()
                    .codeId(c.getCodesId())
                    .version(c.getVersion())
                    .title(c.getTitle())
                    .date(c.getCreatedDate())
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

    private static List<CodeTagsRes> getTags(Codes code) {
        List<CodeTagsRes> tagList = new ArrayList<>();
        if (code.getSelectedTags() != null)
            for (CodeSelectedTags selected : code.getSelectedTags()) {
                tagList.add(
                        CodeTagsRes.builder()
                                .name(selected.getTags().getName())
                                .tagId(selected.getTags().getTagsId())
                                .cnt(selected.getTags().getCnt())
                                .build()
                );
            }
        return tagList;
    }

    public static String parseGithubLink(String link) {
        link = link.replace("https://github.com/", "");
        String[] splitUrl = link.split("/blob/");
        String ownerRepo = splitUrl[0];
        String subUrl = splitUrl[1];
        String[] subUrlParts = subUrl.split("/");
        String branchName = subUrlParts[0];
        String filePath = subUrl.substring(branchName.length());

        String parsedLink = "https://api.github.com/repos/" + ownerRepo + "/contents" + filePath + "?ref=" + branchName;

        return parsedLink;
    }


    public static String reverseParseGithubLink(String parsedLink) {
        parsedLink = parsedLink.replace("https://api.github.com/repos/", "");

        String[] splitUrl = parsedLink.split("/contents/");
        String ownerRepo = splitUrl[0];
        String subUrl = splitUrl[1];

        String[] subUrlParts = subUrl.split("ref=");
        String filePath = subUrlParts[0].substring(0, subUrlParts[0].length() - 1);

        String branchName = subUrlParts[1];

        String reversedLink = "https://github.com/" + ownerRepo + "/blob/" + branchName + "/" + filePath;
        return reversedLink;
    }
}

package com.cody.roughcode.code.service;

import com.cody.roughcode.alarm.dto.req.AlarmReq;
import com.cody.roughcode.alarm.service.AlarmServiceImpl;
import com.cody.roughcode.code.dto.req.CodeReq;
import com.cody.roughcode.code.dto.res.*;
import com.cody.roughcode.code.entity.*;
import com.cody.roughcode.code.repository.*;
import com.cody.roughcode.email.service.EmailServiceImpl;
import com.cody.roughcode.exception.NotMatchException;
import com.cody.roughcode.exception.NotNewestVersionException;
import com.cody.roughcode.exception.SaveFailedException;
import com.cody.roughcode.exception.UpdateFailedException;
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
            codesPage = codeSelectedTagsRepository.findAllByKeywordAndTag(keyword, tagIdList, (long) tagIdList.size(), pageRequest);
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
            connectedProject = projectsRepository.findByProjectsId(req.getProjectId());
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

            // 코드 정보 저장
            CodesInfo codesInfo = CodesInfo.builder()
                    .codes(savedCode)
                    .githubUrl(req.getGithubUrl())
                    .content(req.getContent())
                    .favoriteCnt(0)
                    .language(req.getLanguage()).build();
            codesInfoRepository.save(codesInfo);

            // 반영한 review 저장
            if (req.getSelectedReviewsId() != null) {
                for (Long id : req.getSelectedReviewsId()) {
                    Reviews review = reviewsRepository.findByReviewsId(id);
                    if (review == null) {
                        throw new NullPointerException("일치하는 리뷰가 없습니다.");
                    }
                    if (!review.getCodes().getNum().equals(codeNum)) {
                        throw new NullPointerException("리뷰와 코드가 일치하지 않습니다.");
                    }
                    review.selectedUp();
                    reviewsRepository.save(review);

                    SelectedReviews selectedReviews = SelectedReviews.builder()
                            .reviews(review)
                            .codesInfo(codesInfo)
                            .build();
                    selectedReviewsRepository.save(selectedReviews);

                    // 반영한 리뷰 작성자 목록 저장
                    if (review.getUsers() != null) {
                        reviewAlarm.add(review.getUsers().getUsersId());
                    }
                }
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
                    .content(List.of("북마크한", savedCode.getTitle() + " ver" + (savedCode.getVersion() - 1) + "의 새 버전 ver" + savedCode.getVersion(), "업데이트"))
                    .postId(codeId).build();
            alarmService.insertAlarm(alarmContent);

            emailService.sendAlarm("북마크한 코드가 업데이트되었습니다", alarmContent);
        }
        // 작성한 리뷰가 반영된 무슨무슨 코드 ver1의 새 버전 ver2 업데이트 -> [“작성한 리뷰가 반영된”, “무슨무슨 코드 ver1의 새 버전 ver2”, “업데이트”]
        for (Long id : reviewAlarm) {
            AlarmReq alarmContent = AlarmReq.builder()
                    .section("code")
                    .userId(id)
                    .content(List.of("작성한 리뷰가 반영된", savedCode.getTitle() + " ver" + (savedCode.getVersion() - 1) + "의 새 버전 ver" + savedCode.getVersion(), "업데이트"))
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
        List<Codes> codeList = codesRepository.findByNumAndCodeWriterOrderByVersionDesc(code.getNum(), code.getCodeWriter());

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
                .title(code.getTitle())
                .version(code.getVersion())
                .date(code.getModifiedDate())
                .likeCnt(code.getLikeCnt())
                .reviewCnt(code.getReviewCnt())
                .favoriteCnt(codesInfo.getFavoriteCnt())
                .githubUrl(codesInfo.getGithubUrl())
                .tags(tagList)
                .userId(codeWriterId)
                .userName(codeWriterName)
                .projectId(connectedProjectId)
                .projectTitle(connectedProjectTitle)
                .content(codesInfo.getContent())
                .liked(liked)
                .favorite(favorite)
                .language(codesInfo.getLanguage())
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
        Codes target = codesRepository.findByCodesId(codeId);
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
                for (CodeSelectedTags tag : selectedTagsList) {
                    CodeTags codeTag = tag.getTags();
                    codeTag.cntDown();
                    codeTagsRepository.save(codeTag);

                    codeSelectedTagsRepository.delete(tag);
                }
            } else {
                log.info("기존에 선택하였던 태그가 없습니다");
            }

            // 업데이트한 Tag 등록
            if (req.getSelectedTagsId() != null) {
                List<CodeTags> codeTags = codeTagsRepository.findByTagsIdIn(req.getSelectedTagsId());
                for (CodeTags codeTag : codeTags) {
                    codeSelectedTagsRepository.save(CodeSelectedTags.builder()
                            .tags(codeTag)
                            .codes(target)
                            .build());
                    codeTag.cntUp();
                    codeTagsRepository.save(codeTag);
                }
            } else {
                log.info("새로 선택한 태그가 없습니다");
            }

            // 기존에 선택한 review 삭제
            List<SelectedReviews> selectedReviewsList = targetInfo.getSelectedReviews();
            if (selectedReviewsList != null) {
                for (SelectedReviews review : selectedReviewsList) {
                    Reviews reviews = review.getReviews();
                    reviews.selectedDown();
                    reviewsRepository.save(reviews);

                    selectedReviewsRepository.delete(review);
                }
            } else {
                log.info("기존에 선택하였던 리뷰가 없습니다");
            }

            // 새로 선택한 review 등록
            if (req.getSelectedReviewsId() != null) {
                List<Reviews> reviews = reviewsRepository.findByReviewsIdIn(req.getSelectedReviewsId());
                for (Reviews review : reviews) {
                    selectedReviewsRepository.save(SelectedReviews.builder()
                            .codesInfo(targetInfo)
                            .reviews(review)
                            .build());

                    review.selectedUp();
                    reviewsRepository.save(review);
                }
            } else {
                log.info("새로 선택한 리뷰가 없습니다");
            }

            // 연결된 프로젝트
            Projects connectedProject = null;
            if (req.getProjectId() != null) {
                connectedProject = projectsRepository.findByProjectsId(req.getProjectId());

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
                targetInfo.updateGithubUrl(req.getGithubUrl());
            }

            if (StringUtils.hasText(req.getLanguage())) {
                log.info("코드 정보 수정(코드 언어): " + req.getLanguage());
                targetInfo.updateLanguage(req.getLanguage());
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
    public int deleteCode(Long codeId, Long userId) {
        Users user = usersRepository.findByUsersId(userId);

        // 코드 작성자 확인
        if (user == null) {
            throw new NullPointerException("일치하는 유저가 존재하지 않습니다");
        }

        // 기존 코드 가져오기
        Codes target = codesRepository.findLatestByCodesIdAndUsersId(codeId, userId);
        if (target == null) {
            throw new NullPointerException("일치하는 코드가 존재하지 않습니다");
        }
        if (!target.getCodeWriter().equals(user)) {
            // 코드 작성자와 사용자가 일치하지 않는 경우
            throw new NotMatchException();
        }
        if (!target.getCodesId().equals(codeId)) {
            throw new NotNewestVersionException();
        }

        log.info(target.getTitle());
        CodesInfo targetInfo = codesInfoRepository.findByCodes(target);
        if (targetInfo == null) {
            throw new NullPointerException("일치하는 코드가 존재하지 않습니다");
        }

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

        // 기존 태그 삭제
        List<CodeSelectedTags> selectedTagsList = target.getSelectedTags();
        if (selectedTagsList != null) {
            for (CodeSelectedTags tag : selectedTagsList) {
                CodeTags codeTag = tag.getTags();
                codeTag.cntDown();
                codeTagsRepository.save(codeTag);

                codeSelectedTagsRepository.delete(tag);
            }
        } else {
            log.info("기존에 선택하였던 태그가 없습니다");
        }

        // 기존에 선택한 리뷰 삭제
        List<SelectedReviews> selectedReviewsList = targetInfo.getSelectedReviews();
        if (selectedReviewsList != null) {
            for (SelectedReviews review : selectedReviewsList) {
                Reviews reviews = review.getReviews();
                reviews.selectedDown();
                reviewsRepository.save(reviews);

                selectedReviewsRepository.delete(review);
            }
        } else {
            log.info("기존에 선택하였던 리뷰가 없습니다");
        }

        // 삭제할 코드 아이디
        Long codesId = target.getCodesId();

        // 코드에 등록된 리뷰에 대한 리뷰 좋아요 삭제
        reReviewLikesRepository.deleteAllByCodesId(codesId);

        // 코드에 등록된 리뷰에 대한 리뷰 목록 삭제
        reReviewsRepository.deleteAllByCodesId(codesId);

        // 코드에 등록된 리뷰 좋아요 목록 삭제
        reviewLikesRepository.deleteAllByCodesId(codesId);

        // 코드에 등록된 리뷰 삭제
        reviewsRepository.deleteAllByCodesId(codesId);

        // 코드 좋아요 목록 삭제
        codeLikesRepository.deleteAllByCodesId(codesId);

        // 코드 즐겨찾기 목록 삭제
        codeFavoritesRepository.deleteAllByCodesId(codesId);

        // 코드 정보 삭제
        codesInfoRepository.delete(targetInfo);

        // 코드 삭제
        codesRepository.delete(target);

        log.info("버전 : " + target.getNum());
        // 삭제하는 코드 버전이 1이라면 User의 codeCnt값 1 감소
        if (target.getVersion() == 1) {
            user.codesCntDown();
        }

        return 1;
    }

    @Override
    @Transactional
    public int likeCode(Long codeId, Long userId) {

        Users user = usersRepository.findByUsersId(userId);

        if (user == null) {
            throw new NullPointerException("일치하는 유저가 없습니다");
        }

        Codes target = codesRepository.findByCodesId(codeId);
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
    public List<ReviewInfoRes> getReviewList(Long codeId, Long userId) {
        Users user = usersRepository.findByUsersId(userId);
        if (user == null) {
            throw new NullPointerException("일치하는 유저가 존재하지 않습니다");
        }

        Codes code = codesRepository.findByCodesId(codeId);
        if (code == null) {
            throw new NullPointerException("일치하는 코드가 존재하지 않습니다");
        }

        // num이 일치하는 버전들의 모든 코드 리뷰 목록 조회
        List<Codes> allVersion = codesRepository.findByNumAndCodeWriterOrderByVersionDesc(code.getNum(), user);

        List<ReviewInfoRes> reviewInfoResList = new ArrayList<>();
        for (Codes c : allVersion) {
            for (Reviews r : c.getReviews()) {

                if (r.getCodeContent() == null || r.getCodeContent().equals("")) {
                    continue;
                }
                reviewInfoResList.add(new ReviewInfoRes(r, c.getVersion(), r.getUsers()));
            }
        }

        return reviewInfoResList;
    }

    private List<CodeInfoRes> getCodeInfoRes(Page<Codes> codesPage, Users user) {
        List<Codes> codeList = codesPage.getContent();
        List<CodeInfoRes> codeInfoRes = new ArrayList<>();
        for (Codes c : codeList) {
            List<String> tagList = getTagNames(c);

            // 내가 좋아요 눌렀는지 여부
            CodeLikes codeLikes = codeLikesRepository.findByCodesAndUsers(c, user);
            Boolean liked = codeLikes != null;

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

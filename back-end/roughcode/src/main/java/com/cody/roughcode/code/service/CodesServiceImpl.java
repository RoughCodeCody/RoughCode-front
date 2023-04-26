package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.CodeReq;
import com.cody.roughcode.code.entity.*;
import com.cody.roughcode.code.repository.*;
import com.cody.roughcode.exception.NotMatchException;
import com.cody.roughcode.exception.SaveFailedException;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.repository.ProjectsRepository;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}

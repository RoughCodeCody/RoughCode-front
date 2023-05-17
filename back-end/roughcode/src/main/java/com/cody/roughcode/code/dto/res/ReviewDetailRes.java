package com.cody.roughcode.code.dto.res;

import com.cody.roughcode.code.entity.Reviews;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReviewDetailRes {
    @Schema(description = "기존 코드를 불러올 github URL", example = "https://api.github.com/repos/cody/hello-world/contents/src/main.py?ref=594e05aee256df9e4e826ff56ea2a8c38e9e7972")
    private String githubUrl;

    @Schema(description = "코드 언어", example = "Javascript")
    private String language;

    @Schema(description = "리뷰 id", example = "1")
    private Long reviewId;

    @Schema(description = "리뷰 작성자 id(0이면 익명)", example = "2")
    private Long userId;

    @Schema(description = "리뷰 작성자 닉네임(빈 문자열이면 익명)", example = "코디코디")
    private String userName;

    @Schema(description = "리뷰 내용(코드)", example = "code code code")
    private String codeContent;

    @Schema(description = "리뷰 내용(상세설명)", example = "Rendering 디자인 패턴을 따르는 게 좋습니다.")
    private String content;

    @Schema(description = "코드 선택 부분", example = "[[1, 3], [4, 6]]")
    private List<List<Integer>> lineNumbers;

    @Schema(description = "선택 받은 횟수", example = "5")
    private int selected;

    @Schema(description = "좋아요 수", example = "8")
    private int likeCnt;

    @Schema(description = "내가 좋아요 눌렀는지 여부", example = "true")
    private Boolean liked;

    @Schema(description = "리뷰 정보 작성(수정) 시간", example = "2023-04-13T12:00:00")
    private LocalDateTime date;

    @Schema(description = "리뷰에 대한 리뷰 목록")
    private List<ReReviewRes> reReviews;

    @Schema(description = "리뷰에 대한 리뷰 목록")
    private ReviewCodeRes code;

    public static ReviewDetailRes toDto(String githubUrl, String language, Reviews review, Boolean liked, List<ReReviewRes> reReviews, ReviewCodeRes code) {

        Long userId;
        String userName;
        if (review.getUsers() != null) {
            userId = review.getUsers().getUsersId();
            userName = review.getUsers().getName();
        } else { // 익명인 경우
            userId = 0L;
            userName = "";
        }

        List<List<Integer>> lineNumbers;
        // 선택한 코드 부분
        if (review.getLineNumbers() == null || review.getLineNumbers().equals("[]")) {
            lineNumbers = new ArrayList<>();
        } else {
            lineNumbers = Arrays.stream(review.getLineNumbers().split("\\["))
                    .filter(s -> !s.trim().isEmpty())
                    .map(s -> s.replaceAll("\\[|\\]|\\s", ""))
                    .map(s -> Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toList()))
                    .collect(Collectors.toList());
        }

        return ReviewDetailRes.builder()
                .githubUrl(githubUrl)
                .language(language)
                .reviewId(review.getReviewsId())
                .userId(userId)
                .userName(userName)
                .liked(liked)
                .codeContent(Boolean.TRUE.equals(review.getComplained()) ? "" : review.getCodeContent())
                .content(Boolean.TRUE.equals(review.getComplained()) ? "" : review.getContent())
                .lineNumbers(lineNumbers)
                .likeCnt(review.getLikeCnt())
                .selected(review.getSelected())
                .liked(liked)
                .date(review.getCreatedDate())
                .reReviews(reReviews)
                .code(code)
                .build();
    }
}

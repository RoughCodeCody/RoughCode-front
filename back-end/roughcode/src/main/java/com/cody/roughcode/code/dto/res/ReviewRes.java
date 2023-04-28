package com.cody.roughcode.code.dto.res;

import com.cody.roughcode.code.entity.Reviews;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReviewRes {

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

    @Schema(description = "코드 선택 부분", example = "[1, 3, 4, 6]")
    private List<Integer> lineNumbers;

    @Schema(description = "좋아요 수", example = "8")
    private int likeCnt;

    @Schema(description = "선택 받은 횟수", example = "5")
    private int selected;

    @Schema(description = "내가 좋아요 눌렀는지 여부", example = "true")
    private Boolean liked;

    @Schema(description = "리뷰 정보 작성(수정) 시간", example = "2023-04-13T12:00:00")
    private LocalDateTime date;

    @Schema(description = "리뷰에 대한 리뷰 목록", example = "[ReReview]")
    private List<ReReviewRes> reReviews;

    public ReviewRes(Reviews r, Boolean reviewLiked, List<ReReviewRes> reReviews) {
        this.reviewId = r.getReviewsId();
        if(r.getUsers() != null) {
            this.userId = r.getUsers().getUsersId();
            this.userName = r.getUsers().getName();
        } else { // 익명인 경우
            this.userId = 0L;
            this.userName = "";
        }
        this.codeContent = r.getCodeContent();
        this.content = r.getContent();
        this.lineNumbers = Arrays.stream(r.getLineNumbers().replaceAll("\\[|\\]|\\s", "").split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        this.likeCnt = r.getLikeCnt();
        this.selected = r.getSelected();
        this.liked = reviewLiked;
        this.date = r.getModifiedDate();
        this.reReviews = reReviews;
    }

    public static ReviewRes toDto(Reviews review, Boolean liked, List<ReReviewRes> reReviews) {

        Long userId;
        String userName;
        if(review.getUsers() != null) {
            userId = review.getUsers().getUsersId();
            userName = review.getUsers().getName();
        } else { // 익명인 경우
            userId = 0L;
            userName = "";
        }

        // 선택한 코드 부분
        List<Integer> lineNumbers = Arrays.stream(review.getLineNumbers().replaceAll("\\[|\\]|\\s", "").split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return ReviewRes.builder()
                .reviewId(review.getReviewsId())
                .userId(userId)
                .userName(userName)
                .liked(liked)
                .codeContent(review.getCodeContent())
                .content(review.getContent())
                .lineNumbers(lineNumbers)
                .likeCnt(review.getLikeCnt())
                .selected(review.getSelected())
                .liked(liked)
                .date(review.getModifiedDate())
                .reReviews(reReviews)
                .build();
    }

}

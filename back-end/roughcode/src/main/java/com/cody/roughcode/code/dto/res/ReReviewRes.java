package com.cody.roughcode.code.dto.res;

import com.cody.roughcode.code.entity.ReReviews;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReReviewRes {

    @Schema(description = "리리뷰(리뷰에 대한 리뷰) id", example = "1")
    private Long reReviewId;

    @Schema(description = "리리뷰 남긴 사람 id(0이면 익명)", example = "1")
    private Long userId;

    @Schema(description = "리리뷰 남긴 사람 닉네임(빈 문자열이면 익명)", example = "코디")
    private String userName;

    @Schema(description = "코드 리리뷰 좋아요 수", example = "3")
    private int likeCnt;

    @Schema(description = "내가 좋아요 눌렀는지 여부", example = "true")
    private Boolean liked;

    @Schema(description = "리리뷰 내용", example = "JS는 JS 답게 쓰는게 좋아요")
    private String content;

    @Schema(description = "리리뷰 작성 시간", example = "2023-04-13T12:00:00")
    private LocalDateTime createdDate;

    @Schema(description = "리리뷰 수정 시간 ", example = "2023-04-17T13:40:00")
    private LocalDateTime modifiedDate;

    public static ReReviewRes toDto(ReReviews reReviews, Boolean liked) {

        Long userId;
        String userName;

        if (reReviews.getUsers() != null) {
            userId = reReviews.getUsers().getUsersId();
            userName = reReviews.getUsers().getName();
        } else { // 익명인 경우
            userId = 0L;
            userName = "";
        }

        return ReReviewRes.builder()
                .reReviewId(reReviews.getReReviewsId())
                .userId(userId)
                .userName(userName)
                .likeCnt(reReviews.getLikeCnt())
                .liked(liked)
                .content(Boolean.TRUE.equals(reReviews.getComplained()) ? "" : reReviews.getContent())
                .createdDate(reReviews.getCreatedDate())
                .modifiedDate(reReviews.getModifiedDate())
                .build();
    }

}

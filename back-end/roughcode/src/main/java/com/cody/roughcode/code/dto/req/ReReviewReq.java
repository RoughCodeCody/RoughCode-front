package com.cody.roughcode.code.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReReviewReq {
    @Schema(description = "리뷰 아이디", example = "1")
    @Positive(message = "reviewId 값이 범위를 벗어납니다")
    Long reviewsId;

    @Schema(description = "리-리뷰 내용", example = "이 말이 맞습니다!")
    @NotEmpty(message = "리-리뷰는 비어있을 수 없습니다")
    @Size(max = 500, message = "리-리뷰는 500자를 넘을 수 없습니다")
    String content;
}

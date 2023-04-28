package com.cody.roughcode.code.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SelectedReviewRes {

    @Schema(description = "리뷰 id", example = "1")
    private Long reviewId;

    @Schema(description = "리뷰 작성자 닉네임", example = "코디")
    private String userName;

    @Schema(description = "리뷰 작성 내용", example = "컨벤션을 준수하세요")
    private String content;

}

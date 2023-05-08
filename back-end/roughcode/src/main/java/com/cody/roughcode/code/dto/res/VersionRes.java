package com.cody.roughcode.code.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class VersionRes {

    @Schema(description = "코드 id", example = "1")
    private Long codeId;

    @Schema(description = "코드 제목", example = "이러이러하게 했는데 좀 봐주세요")
    private String title;

    @Schema(description = "코드 버전", example = "2")
    private int version;

    @Schema(description = "반영된 리뷰 목록")
    private List<SelectedReviewRes> selectedReviews;
}

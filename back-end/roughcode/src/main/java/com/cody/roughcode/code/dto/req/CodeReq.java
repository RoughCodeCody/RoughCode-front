package com.cody.roughcode.code.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeReq {

    @Schema(description = "코드 제목", example = "개발새발 코드")
    private String title;

    @Schema(description = "코드 내용을 담은 github URL", example = "")
    private String githubUrl;

    @Schema(description = "코드 설명", example = "시간 초과가 뜹니다. 더 효율적인 방법이 궁금합니다!")
    private String content;

    @Schema(description = "코드 id(버전 업데이트가 아니면 -1)", example = "-1")
    private Long codeId;

    @Schema(description = "연결된 프로젝트 id", example = "2L")
    private Long projectId;

    @Schema(description = "선택한 tag의 id", example = "[1, 2, 3]")
    private List<Long> selectedTagsId;

    @Schema(description = "선택한 review의 id", example = "[1, 2, 3]")
    private List<Long> selectedReviewsId;
}

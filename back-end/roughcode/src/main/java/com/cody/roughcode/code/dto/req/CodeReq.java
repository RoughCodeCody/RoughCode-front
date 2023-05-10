package com.cody.roughcode.code.dto.req;

import com.cody.roughcode.validation.EachPositive;
import com.cody.roughcode.validation.NotZero;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeReq {

    @Schema(description = "코드 제목", example = "개발새발 코드")
    @NotEmpty(message = "코드 제목은 비어있을 수 없습니다")
    private String title;

    @Schema(description = "코드 내용을 담은 github URL", example = "https://api.github.com/repos/charmdew/print/contents/print.c?ref=b4ef643067a1a0a83f45e2bd2945ea352ec06bc0")
    @NotEmpty(message = "코드를 불러올 github URL은 비어있을 수 없습니다")
    private String githubUrl;

    @Schema(description = "코드 설명", example = "시간 초과가 뜹니다. 더 효율적인 방법이 궁금합니다!")
    private String content;

    @Schema(description = "코드 id(버전 업데이트가 아니면 -1)", example = "-1")
    @Range(min = -1, max = Long.MAX_VALUE, message = "codeId 값이 범위를 벗어납니다")
    @NotZero(message = "codeId 값이 범위를 벗어납니다")
    private Long codeId;

    @Schema(description = "연결된 프로젝트 id", example = "1")
    @Positive(message = "projectId 값은 null 이거나 1 이상이어야 합니다")
    private Long projectId;

    @Schema(description = "선택한 tag의 id", example = "[1, 2, 3]")
    @EachPositive(message = "selectedTagsId 리스트 안의 값은 1 이상이어야 합니다")
    private List<Long> selectedTagsId;

    @Schema(description = "선택한 review의 id", example = "[1, 2, 3]")
    @EachPositive(message = "selectedReviewsId 리스트 안의 값은 1 이상이어야 합니다")
    private List<Long> selectedReviewsId;

    @Schema(description = "코드 언어", example = "C")
    @NotEmpty(message = "코드 언어는 비어있을 수 없습니다")
    private String language;
}

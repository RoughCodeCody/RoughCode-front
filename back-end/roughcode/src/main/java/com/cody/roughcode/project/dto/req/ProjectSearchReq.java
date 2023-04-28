package com.cody.roughcode.project.dto.req;

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
public class ProjectSearchReq {
    @Schema(description = "검색할 내용", example = "개발새발")
    private String keyword;

    @Schema(description = "검색할 태그 아이디 리스트", example = "[1, 2, 3]")
    private List<Long> tagIdList;

    @Schema(description = "닫힌것을 포함할거면 true, 아니면 false", example = "false")
    private Boolean closed;
}

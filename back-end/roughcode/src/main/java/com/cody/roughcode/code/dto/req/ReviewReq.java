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
public class ReviewReq {

    @Schema(description = "리뷰를 등록하는 코드 id", example = "1")
    private Long codeId;

    @Schema(description = "선택된 구간", example = "[[1, 2], [4, 6]]")
    private List<List<Integer>> selectedRange;

    @Schema(description = "코드 내용", example = "import heapq ~~")
    private String codeContent;

    @Schema(description = "상세 설명", example = "최소힙을 쓰면 시간을 줄일 수 있어요")
    private String content;

}

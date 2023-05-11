package com.cody.roughcode.code.dto.req;

import com.cody.roughcode.validation.EachPositive2D;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReq {

    @Schema(description = "리뷰를 등록하는 코드 id", example = "1")
    private Long codeId;

    @Schema(description = "선택된 구간", example = "[[1, 2], [4, 6]]")
    @EachPositive2D(message = "selectedRange 리스트 안의 값은 1 이상이어야 합니다")
    private List<List<Integer>> selectedRange;

    @Schema(description = "코드 내용(Base64)", example = "SGVsbG8gV29ybGQ=")
    @NotEmpty(message = "코드 내용은 비어있을 수 없습니다")
    private String codeContent;

    @Schema(description = "상세 설명", example = "최소힙을 쓰면 시간을 줄일 수 있어요")
    @NotEmpty(message = "상세 설명은 비어있을 수 없습니다")
    private String content;

}

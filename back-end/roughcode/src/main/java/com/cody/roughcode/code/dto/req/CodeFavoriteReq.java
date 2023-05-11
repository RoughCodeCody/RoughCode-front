package com.cody.roughcode.code.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeFavoriteReq {

    @Schema(description = "즐겨찾기 내용", example = "오.. DP를 활용한 효율적인 코드")
    @Size(max = 200, message = "내용은 200자를 넘을 수 없습니다")
    private String content;

}


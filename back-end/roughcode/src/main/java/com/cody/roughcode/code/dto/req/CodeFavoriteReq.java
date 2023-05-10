package com.cody.roughcode.code.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeFavoriteReq {

    @Schema(description = "즐겨찾기 내용", example = "오.. DP를 활용한 효율적인 코드")
    private String content;

}

public void bugExample() {
    String unused = "This variable is unused";
}

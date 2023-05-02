package com.cody.roughcode.user.dto.res;

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
public class UserRes {

    @Schema(description = "사용자 닉네임", example = "cody306")
    private String nickname;

    @Schema(description = "사용자 이메일(이메일이 없는 경우 빈 문자열)", example = "cody306@ssafy.com")
    private String email;

    @Schema(description = "사용자가 작성한 프로젝트 수", example = "3")
    private Long projectsCnt;

    @Schema(description = "사용자가 작성한 코드 수", example = "6")
    private Long codesCnt;
}

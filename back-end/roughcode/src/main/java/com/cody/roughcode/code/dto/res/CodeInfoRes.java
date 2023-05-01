package com.cody.roughcode.code.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeInfoRes {
    
    @Schema(description = "코드 id", example = "1")
    private Long codeId;

    @Schema(description = "코드 버전", example = "1")
    private int version;

    @Schema(description = "코드 제목", example = "이 코드 좀 봐주세용")
    private String title;

    @Schema(description = "코드 정보 작성(수정) 시간", example = "2023-04-17T13:40:00")
    private LocalDateTime date;

    @Schema(description = "좋아요 수", example = "13")
    private int likeCnt;

    @Schema(description = "리뷰 수", example = "3")
    private int reviewCnt;

    @Schema(description = "등록한 태그 이름들", example = "[TypeScript, React]")
    private List<String> tags;

    @Schema(description = "코드 정보 작성자 닉네임", example = "아이유")
    private String userName;

    @Schema(description = "내가 좋아요 눌렀는지 여부", example = "false")
    private Boolean liked;
    
}

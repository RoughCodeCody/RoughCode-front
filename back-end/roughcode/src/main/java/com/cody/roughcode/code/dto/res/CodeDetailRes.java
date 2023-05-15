package com.cody.roughcode.code.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CodeDetailRes {

    @Schema(description = "코드 id", example = "1")
    private Long codeId;

    @Schema(description = "내가 작성한 코드인지 여부", example = "true")
    private Boolean mine;

    @Schema(description = "코드 제목", example = "이 코드 좀 봐주세용")
    private String title;

    @Schema(description = "코드 버전", example = "1")
    private int version;

    @Schema(description = "코드 정보 작성(수정) 시간", example = "2023-04-17T13:40:00")
    private LocalDateTime date;

    @Schema(description = "좋아요 수", example = "13")
    private int likeCnt;

    @Schema(description = "리뷰 수", example = "3")
    private int reviewCnt;

    @Schema(description = "즐겨찾기 수", example = "13")
    private int favoriteCnt;

    @Schema(description = "코드를 불러올 github URL", example = "https://api.github.com/repos/cody/hello-world/contents/src/main.py?ref=594e05aee256df9e4e826ff56ea2a8c38e9e7972")
    private String githubUrl;

    @Schema(description = "등록한 태그 이름들", example = "[TypeScript, React]")
    private List<String> tags;

    @Schema(description = "코드 정보 작성자 id", example = "1")
    private Long userId;

    @Schema(description = "코드 정보 작성자 닉네임", example = "아이유")
    private String userName;

    @Schema(description = "연결된 프로젝트 id", example = "1")
    private Long projectId;

    @Schema(description = "연결된 프로젝트 제목(없을 경우 null)", example = "Sense and Sensibility")
    private String projectTitle;

    @Schema(description = "코드 상세 설명", example = "이 코드는 시간초과가 뜹니다")
    private String content;

    @Schema(description = "내가 좋아요 눌렀는지 여부", example = "false")
    private Boolean liked;

    @Schema(description = "내가 즐겨찾기 눌렀는지 여부", example = "true")
    private Boolean favorite;

    @Schema(description = "코드 언어", example = "Javascript")
    private String language;

    @Schema(description = "코드가 최신 버전인지 여부", example = "true")
    private Boolean latest;

    @Schema(description = "버전 정보")
    private List<VersionRes> versions;

    @Schema(description = "코드에 달린 리뷰 목록 정보")
    private List<ReviewRes> reviews;

}

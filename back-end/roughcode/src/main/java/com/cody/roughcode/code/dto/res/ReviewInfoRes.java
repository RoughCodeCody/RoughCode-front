package com.cody.roughcode.code.dto.res;

import com.cody.roughcode.code.entity.Reviews;
import com.cody.roughcode.user.entity.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReviewInfoRes {
    @Schema(description = "리뷰 id", example = "1")
    private Long reviewId;

    @Schema(description = "리뷰 작성자 id(0이면 익명)", example = "2")
    private Long userId;

    @Schema(description = "리뷰 작성자 닉네임(빈 문자열이면 익명)", example = "코디코디")
    private String userName;

    @Schema(description = "리뷰 내용(코드)", example = "code code code")
    private String codeContent;

    @Schema(description = "리뷰 내용(상세설명)", example = "Rendering 디자인 패턴을 따르는 게 좋습니다.")
    private String content;

    @Schema(description = "코드 선택 부분", example = "[[1, 3], [4, 6]]")
    private List<List<Integer>> lineNumbers;

    @Schema(description = "선택 받은 횟수", example = "5")
    private Boolean selected;

    @Schema(description = "버전 정보", example = "2")
    private int version;

    public ReviewInfoRes(Reviews review, int version, Users user) {
        List<List<Integer>> lineNumbers;
        // 선택한 코드 부분
        if (review.getLineNumbers() == null || review.getLineNumbers().equals("[]")) {
            lineNumbers = new ArrayList<>();
        } else {
            lineNumbers = Arrays.stream(review.getLineNumbers().split("\\["))
                    .filter(s -> !s.trim().isEmpty())
                    .map(s -> s.replaceAll("\\[|\\]|\\s", ""))
                    .map(s -> Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toList()))
                    .collect(Collectors.toList());
        }

        this.reviewId = review.getReviewsId();
        if (user != null) {
            this.userId = user.getUsersId();
            this.userName = user.getName();
        } else {
            this.userId = 0L;
            this.userName = "";
        }
        this.codeContent = review.getCodeContent();
        this.content = review.getContent();
        this.lineNumbers = lineNumbers;
        this.selected = review.getSelected() > 0;
        this.version = version;
    }
}

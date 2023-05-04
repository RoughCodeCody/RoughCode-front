package com.cody.roughcode.project.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FeedbackUpdateReq {
    @Schema(description = "피드백 아이디", example = "1")
    @Positive(message = "feedbackId 값이 범위를 벗어납니다")
    Long feedbackId;

    @Schema(description = "피드백 내용", example = "개발새발 좋네요")
    @NotEmpty
    @Size(max = 500, message = "피드백은 500자를 넘을 수 없습니다")
    String content;
}

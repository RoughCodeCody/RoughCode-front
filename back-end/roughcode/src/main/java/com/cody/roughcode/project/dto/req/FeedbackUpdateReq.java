package com.cody.roughcode.project.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FeedbackUpdateReq {
    @Schema(description = "피드백 아이디", example = "1")
    Long feedbackId;

    @Schema(description = "피드백 내용", example = "개발새발 좋네요")
    String content;
}

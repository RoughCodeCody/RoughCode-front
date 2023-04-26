package com.cody.roughcode.project.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectedFeedbacksRes {
    private Long feedbackId;
    private String content;
}

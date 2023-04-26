package com.cody.roughcode.project.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionRes {
    private Long projectId;
    private int version;
    private String notice;
    private List<SelectedFeedbacksRes> selectedFeedbacks;
}

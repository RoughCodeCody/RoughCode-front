package com.cody.roughcode.project.dto.res;

import com.cody.roughcode.project.entity.ProjectTags;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTagsRes {
    private Long tagId;
    private String name;
    private int cnt;

    public ProjectTagsRes(ProjectTags tags) {
        this.tagId = tags.getTagsId();
        this.name = tags.getName();
        this.cnt = tags.getCnt();
    }
}

package com.cody.roughcode.code.dto.res;

import com.cody.roughcode.code.entity.CodeTags;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeTagsRes {
    private Long tagId;
    private String name;
    private int cnt;

    public CodeTagsRes(CodeTags tags) {
        this.tagId = tags.getTagsId();
        this.name = tags.getName();
        this.cnt = tags.getCnt();
    }
}

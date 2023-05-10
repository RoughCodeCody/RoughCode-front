package com.cody.roughcode.code.dto.res;

import com.cody.roughcode.code.entity.CodeTags;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeTagsRes {
    @Schema(description = "태그 id", example = "1")
    private Long tagId;

    @Schema(description = "태그 이름", example = "Javascript")
    private String name;

    @Schema(description = "태그 사용 횟수", example = "3")
    private int cnt;

    public CodeTagsRes(CodeTags tags) {
        this.tagId = tags.getTagsId();
        this.name = tags.getName();
        this.cnt = tags.getCnt();
    }
}

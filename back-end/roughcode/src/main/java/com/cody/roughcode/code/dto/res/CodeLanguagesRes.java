package com.cody.roughcode.code.dto.res;

import com.cody.roughcode.code.entity.CodeLanguages;
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
public class CodeLanguagesRes {
    @Schema(description = "언어 id", example = "1")
    private Long languageId;

    @Schema(description = "언어 이름", example = "Javascript")
    private String name;

    @Schema(description = "언어 사용 횟수", example = "3")
    private int cnt;

    public CodeLanguagesRes(CodeLanguages languages) {
        this.languageId = languages.getLanguagesId();
        this.name = languages.getName();
        this.cnt = languages.getCnt();
    }
}

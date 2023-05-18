package com.cody.roughcode.project.dto.res;

import com.cody.roughcode.code.entity.Codes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeInfoRes {
    private Long codeId;
    private String title;
    private List<String> tags;

    public CodeInfoRes(Codes code) {
        this.codeId = code.getCodesId();
        this.title = code.getTitle();
        this.tags = new ArrayList<>();
        if(code.getSelectedTags() != null)
            for(var codeTag : code.getSelectedTags()){
                this.tags.add(codeTag.getTags().getName());
            }
    }
}

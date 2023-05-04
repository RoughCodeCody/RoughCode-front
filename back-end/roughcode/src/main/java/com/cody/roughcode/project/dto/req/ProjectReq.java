package com.cody.roughcode.project.dto.req;

import com.cody.roughcode.project.entity.ProjectSelectedTags;
import com.cody.roughcode.validation.EachPositive;
import com.cody.roughcode.validation.NotZero;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectReq {

    @Schema(description = "프로젝트 이름", example = "개발새발")
    @NotEmpty
    @Size(max = 15, message = "프로젝트 이름은 15자를 넘을 수 없습니다")
    private String title;

    @Schema(description = "프로젝트 한 줄 정보", example = "토이 프로젝트를 공유할 수 있는 사이트입니다")
    @NotEmpty
    @Size(max = 50, message = "프로젝트 한 줄 소개는 50자를 넘을 수 없습니다")
    private String introduction;

    @Schema(description = "프로젝트 설명", example = "토이 프로젝트를 공유할 수 있는 사이트입니다 SpringBoot와 Next.js를 사용했습니다")
    private String content;

    @Schema(description = "프로젝트 url", example = "https://www.google.com")
    @NotEmpty
    @Pattern(regexp = "^https?://.*", message = "Url 값은 http 또는 https로 시작해야 합니다")
    private String url;

    @Schema(description = "프로젝트 공지사항", example = "방금 막 완성했습니다")
    @NotEmpty
    @Size(max = 50, message = "공지사항은 50자를 넘을 수 없습니다")
    private String notice;

    @Schema(description = "프로젝트 id(버전 업데이트가 아니면 -1)", example = "-1")
    @Range(min = -1, max = Integer.MAX_VALUE, message = "projectId 값이 범위를 벗어납니다")
    @NotZero(message = "projectId 값이 범위를 벗어납니다")
    private Long projectId;


    @Schema(description = "선택한 tag의 id", example = "[1, 2, 3]")
    @EachPositive(message = "selectedTagsId 리스트 안의 값은 1 이상이어야 합니다")
    private List<Long> selectedTagsId;

    @Schema(description = "선택한 feedback id", example = "[1, 2, 3]")
    @EachPositive(message = "selectedFeedbacksId 리스트 안의 값은 1 이상이어야 합니다")
    private List<Long> selectedFeedbacksId;
}

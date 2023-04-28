package com.cody.roughcode.project.dto.req;

import com.cody.roughcode.project.entity.ProjectSelectedTags;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectReq {

    @Schema(description = "프로젝트 이름", example = "개발새발")
    private String title;

    @Schema(description = "프로젝트 한 줄 정보", example = "토이 프로젝트를 공유할 수 있는 사이트입니다")
    private String introduction;

    @Schema(description = "프로젝트 설명", example = "토이 프로젝트를 공유할 수 있는 사이트입니다 SpringBoot와 Next.js를 사용했습니다")
    private String content;

    @Schema(description = "프로젝트 url", example = "https://www.google.com")
    private String url;

    @Schema(description = "프로젝트 공지사항", example = "방금 막 완성했습니다")
    private String notice;

    @Schema(description = "프로젝트 id(버전 업데이트가 아니면 -1)", example = "-1")
    private Long projectId;

    @Schema(description = "선택한 tag의 id", example = "[1, 2, 3]")
    private List<Long> selectedTagsId;

    @Schema(description = "선택한 feedback id", example = "[1, 2, 3]")
    private List<Long> selectedFeedbacksId;
}

package com.cody.roughcode.project.entity;

import com.cody.roughcode.code.entity.Codes;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_selected_tags")
public class ProjectSelectedTags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selected_tags_id", nullable = false, columnDefinition = "BIGINT")
    private Long selectedTagsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tags_id", nullable = false)
    private ProjectTags tags;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projects_id", nullable = false)
    private Projects projects;
}

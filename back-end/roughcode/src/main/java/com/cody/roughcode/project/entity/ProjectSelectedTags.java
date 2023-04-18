package com.cody.roughcode.project.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_selected_tags")
public class ProjectSelectedTags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selected_tags_id", nullable = false, columnDefinition = "BIGINT ")
    private Long selectedTagsId;

    @ManyToOne
    @JoinColumn(name="tags_id")
    private ProjectTags tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "projects_id", referencedColumnName = "projects_id", insertable = false, updatable = false),
            @JoinColumn(name = "version", referencedColumnName = "version", insertable = false, updatable = false)
    })
    private Projects projects;
}

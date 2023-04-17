package com.cody.roughcode.project.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_tags")
public class ProjectTags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long tagId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "cnt", nullable = true)
    private int cnt = 0;

}

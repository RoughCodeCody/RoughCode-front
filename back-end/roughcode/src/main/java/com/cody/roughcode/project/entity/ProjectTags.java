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
    @Column(name = "tags_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long tagsId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Builder.Default
    @Column(name = "cnt", nullable = true)
    private int cnt = 0;

    public void cntUp() {
        this.cnt += 1;
    }

    public void cntDown() { this.cnt -= 1; }
}

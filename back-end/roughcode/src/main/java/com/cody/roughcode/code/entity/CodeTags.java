package com.cody.roughcode.code.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "code_tags")
public class CodeTags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long tagId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "cnt", nullable = true)
    private int cnt = 0;

}

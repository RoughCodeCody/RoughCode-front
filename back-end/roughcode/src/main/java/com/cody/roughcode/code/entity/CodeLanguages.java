package com.cody.roughcode.code.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "code_languages")
public class CodeLanguages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "languages_id", nullable = false, columnDefinition = "BIGINT")
    private Long languagesId;

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

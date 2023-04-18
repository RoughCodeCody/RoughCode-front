package com.cody.roughcode.code.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "code_selected_tags")
public class CodeSelectedTags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selected_tags_id", nullable = false, columnDefinition = "BIGINT ")
    private Long selectedTagsId;

    @ManyToOne
    @JoinColumn(name="tags_id")
    private CodeTags tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "codes_id", referencedColumnName = "codes_id", insertable = false, updatable = false),
            @JoinColumn(name = "version", referencedColumnName = "version", insertable = false, updatable = false)
    })
    private Codes codes;
}

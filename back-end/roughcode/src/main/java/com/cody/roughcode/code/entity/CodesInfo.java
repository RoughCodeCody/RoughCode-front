package com.cody.roughcode.code.entity;

import com.cody.roughcode.project.entity.CodeFavorites;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.entity.SelectedFeedbacks;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "codes_info")
public class CodesInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long id;

    @Builder.Default
    @Column(name = "github_url", nullable = false)
    private String githubUrl = "";

    @Builder.Default
    @Column(name = "content", nullable = true, columnDefinition = "text")
    private String content = "";

    @Builder.Default
    @Column(name = "favorite_cnt", nullable = true)
    private int favoriteCnt = 0;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codes_id", nullable = false)
    private Codes codes;

    @OneToMany(mappedBy = "codes", fetch = FetchType.LAZY)
    private List<Reviews> reviews;

    @OneToMany(mappedBy = "codes", fetch = FetchType.LAZY)
    private List<SelectedReviews> selectedReviews;

    @OneToMany(mappedBy = "codes", fetch = FetchType.LAZY)
    private List<CodeFavorites> codeFavorites;

    @OneToMany(mappedBy = "codes", fetch = FetchType.LAZY)
    private List<CodeLikes> codeLikes;
}

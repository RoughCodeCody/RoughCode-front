package com.cody.roughcode.code.entity;

import com.cody.roughcode.code.dto.req.CodeReq;
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
    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
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

    @Builder.Default
    @Column(name = "language", nullable = true)
    private String language = "";

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

    public void updateCode(CodeReq req) {
        this.content = req.getContent();
        this.githubUrl = req.getGithubUrl();
    }

    public void favoriteCntUp() {
        this.favoriteCnt += 1;
    }

    public void favoriteCntDown() {
        this.favoriteCnt -= 1;
    }

    public void updateContent(String content){
        this.content = content;
    }

    public void updateGithubUrl(String githubUrl){
        this.githubUrl = githubUrl;
    }

    public void updateLanguage(String language){
        this.language = language;
    }
}

package com.cody.roughcode.code.entity;

import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.util.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Reviews extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviews_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long reviewsId;

    @Builder.Default
    @Column(name = "like_cnt", nullable = true)
    private int likeCnt = 0;

    @Builder.Default
    @Column(name = "complaint", nullable = true, columnDefinition = "text")
    private String complaint = "";

    @Builder.Default
    @Column(name = "line_numbers", nullable = true, columnDefinition = "text")
    private String lineNumbers = "";

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "code_content", columnDefinition = "longtext")
    private String codeContent;

    @Builder.Default
    @Column(name = "selected", nullable = true)
    private int selected = 0;

    @JsonManagedReference
    @OneToMany(mappedBy = "reviews")
    private List<ReReviews> reReviews;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codes_id", nullable = false)
    private Codes codes;

    @Builder.Default
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users = null;

    @JsonManagedReference
    @OneToMany(mappedBy = "reviews")
    private List<ReviewLikes> reviewLikes;

    public void selectedUp() {
        this.selected += 1;
    }

    public void selectedDown() {
        this.selected -= 1;
    }

    public void likeCntUp() {
        this.likeCnt += 1;
    }

    public void likeCntDown() {
        this.likeCnt -= 1;
    }

    public void updateLineNumbers(List<List<Integer>> selectedRange) {
        this.lineNumbers = selectedRange.toString();
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateCodeContent(String codeContent) {
        this.codeContent = codeContent;
    }

    public void deleteCodeContent() {
        this.codeContent = "";
    }

    public void setComplaint(List<String> complainList) {
        this.complaint = String.join(",", complainList);
    }

    public void setReReviews(ReReviews savedReReview) {
        if (this.reReviews == null) {
            this.reReviews = new ArrayList<>();
        }
        this.reReviews.add(savedReReview);
    }
}

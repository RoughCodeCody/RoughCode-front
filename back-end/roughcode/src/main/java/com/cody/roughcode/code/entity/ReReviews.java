package com.cody.roughcode.code.entity;

import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.util.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rereviews")
public class ReReviews extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rereviews_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long reReviewsId;

    @Builder.Default
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users = null;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Builder.Default
    @Column(name = "like_cnt", nullable = true)
    private int likeCnt = 0;

    @Builder.Default
    @Column(name = "complaint", nullable = true)
    private String complaint = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviews_id", nullable = false)
    private Reviews reviews;

    public void setContent(String content) {
        this.content = content;
    }

    public void likeCntUp() {
        this.likeCnt += 1;
    }
    public void likeCntDown() {
        this.likeCnt -= 1;
    }

    public void deleteContent() {
        this.content = "";
    }

    public void setComplaint(List<String> complainList) {
        this.complaint = String.join(",", complainList);
    }
}

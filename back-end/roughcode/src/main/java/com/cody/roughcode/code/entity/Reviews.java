package com.cody.roughcode.code.entity;

import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
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
    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content = "";

    @Builder.Default
    @Column(name = "like_cnt", nullable = true)
    private int likeCnt = 0;

    @Builder.Default
    @Column(name = "complaint", nullable = true)
    private int complaint = 0;

    @Builder.Default
    @Column(name = "line_numbers", nullable = true, columnDefinition = "text")
    private String lineNumbers = "";

    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    @Column(name = "review_code", columnDefinition = "longtext")
    private String reviewCode;

    @Builder.Default
    @Column(name = "selected", nullable = true)
    private boolean selected = false;


    @OneToMany(mappedBy = "reviews")
    private List<ReReviews> reviewsRereviewss;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codes_id", nullable = false)
    private Codes codes;

    @Builder.Default
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users = null;
}

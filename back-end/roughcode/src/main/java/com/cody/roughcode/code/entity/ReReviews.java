package com.cody.roughcode.code.entity;

import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users = null;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "likes", nullable = true)
    private int likes = 0;

    @Column(name = "complaint", nullable = true)
    private int complaint = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviews_id", nullable = false)
    private Reviews reviews;
}

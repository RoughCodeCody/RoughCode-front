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

    @Column(name = "content", length = 255, nullable = true)
    private String content = "";

    @Column(name = "likes", nullable = true)
    private int likes = 0;

    @Column(name = "complaint", nullable = true)
    private int complaint = 0;

    @ManyToOne
    @JoinColumn(name="users_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name="reviews_id")
    private Reviews reviews;
}

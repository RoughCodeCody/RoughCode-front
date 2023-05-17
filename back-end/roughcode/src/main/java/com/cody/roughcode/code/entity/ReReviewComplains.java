package com.cody.roughcode.code.entity;

import com.cody.roughcode.user.entity.Users;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rereview_complains")
public class ReReviewComplains {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complains_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long complainsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rereviews_id", nullable = false)
    private ReReviews reReviews;

}

package com.cody.roughcode.code.entity;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "selected_reviews")
public class SelectedReviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selected_reviews_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long selectedReviewsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviews_id", nullable = false)
    private Reviews reviews;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_codes_id", nullable = false)
    private CodesInfo codesInfo;
}

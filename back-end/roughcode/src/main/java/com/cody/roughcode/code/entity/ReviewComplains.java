package com.cody.roughcode.code.entity;

import com.cody.roughcode.user.entity.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review_complains")
public class ReviewComplains {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complains_id", nullable = false, columnDefinition = "BIGINT")
    private Long complainsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviews_id", nullable = false)
    private Reviews reviews;

}

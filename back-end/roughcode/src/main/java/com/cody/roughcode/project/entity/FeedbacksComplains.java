package com.cody.roughcode.project.entity;

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
@Table(name = "feedbacks_complains")
public class FeedbacksComplains {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedbakcs_complains_id", nullable = false, columnDefinition = "BIGINT")
    private Long feedbacksComplainsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedbacks_id", nullable = false)
    private Feedbacks feedbacks;
}

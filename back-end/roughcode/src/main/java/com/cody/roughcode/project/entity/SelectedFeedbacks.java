package com.cody.roughcode.project.entity;

import com.cody.roughcode.code.entity.Codes;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "selected_feedbacks")
public class SelectedFeedbacks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selected_feedbacks_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long selectedTagsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedbacks_id", nullable = false)
    private Feedbacks feedbacks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_project_id", nullable = false)
    private Projects projects;
}

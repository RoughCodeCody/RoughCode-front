package com.cody.roughcode.project.entity;

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
@Table(name = "feedbacks")
public class Feedbacks extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedbacks_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long feedbacksId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "projects_id", referencedColumnName = "projects_id"),
            @JoinColumn(name = "version", referencedColumnName = "version")
    })
    private Projects projects;

    @Column(name = "content", length = 255, nullable = true)
    private String content = "";

    @Column(name = "likes", nullable = true)
    private int likes = 0;

    @Column(name = "complaint", nullable = true)
    private int complaint = 0;

    @Column(name = "selected", nullable = true)
    private boolean selected = false;

    @Column(name = "comment", length = 255, nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name="users_id")
    private Users users;
}

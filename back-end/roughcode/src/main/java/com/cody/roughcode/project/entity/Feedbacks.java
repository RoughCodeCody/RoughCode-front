package com.cody.roughcode.project.entity;

import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedbacks")
public class Feedbacks extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedbacks_id", nullable = false, columnDefinition = "BIGINT")
    private Long feedbacksId;

    @Builder.Default
    @Column(name = "content", nullable = true, columnDefinition = "text")
    private String content = "";

    @Builder.Default
    @Column(name = "like_cnt", nullable = true)
    private int likeCnt = 0;

    @Builder.Default
    @Column(name = "complaint", nullable = true)
    private int complaint = 0;

    @Builder.Default
    @Column(name = "selected", nullable = true)
    private int selected = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private ProjectsInfo projectsInfo;

    @Builder.Default
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = true)
    private Users users = null;

    public void selectedUp() {
        this.selected += 1;
    }

    public void selectedDown() {
        this.selected -= 1;
    }
}

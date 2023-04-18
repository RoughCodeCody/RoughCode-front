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
    @Column(name = "reviews_id", nullable = false, columnDefinition = "BIGINT ")
    private Long reviewsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "codes_id", referencedColumnName = "codes_id"),
            @JoinColumn(name = "version", referencedColumnName = "version")
    })
    private Codes codes;

    @Builder.Default
    @Column(name = "content", length = 255, nullable = true)
    private String content = "";

    @Builder.Default
    @Column(name = "likes", nullable = true)
    private int likes = 0;

    @Builder.Default
    @Column(name = "complaint", nullable = true)
    private int complaint = 0;

    @Column(name = "start_line", nullable = false)
    private String startLine;

    @Column(name = "end_line", nullable = false)
    private String endLine;

    @Builder.Default
    @Column(name = "selected", nullable = true)
    private boolean selected = false;

    @Column(name = "comment", length = 255, nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name="users_id")
    private Users users;

    @Column(name = "review_code", length = 255, nullable = false)
    private String reviewCode;

    @OneToMany(mappedBy = "reviews")
    List<ReReviews> reReviews;
}

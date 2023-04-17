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
    @Column(name = "reviews_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long reviewsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "codes_id", referencedColumnName = "codes_id"),
            @JoinColumn(name = "version", referencedColumnName = "version")
    })
    private Codes codes;

    @Column(name = "content", length = 255, nullable = true)
    private String content = "";

    @Column(name = "like", nullable = true)
    private int like = 0;

    @Column(name = "complaint", nullable = true)
    private int complaint = 0;

    @Column(name = "start", nullable = false)
    private int start;

    @Column(name = "end", nullable = false)
    private int end;

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

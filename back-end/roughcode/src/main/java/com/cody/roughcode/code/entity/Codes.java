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
@Table(name = "codes")
public class Codes extends BaseTimeEntity {
    @EmbeddedId
    private CodeId codesId;

    @Column(name = "title", length = 20, nullable = false)
    private String title;

    @Column(name = "like", nullable = true)
    private int like = 0;

    @Column(name = "favorite", nullable = true)
    private int favorite = 0;

    @Column(name = "review_cnt", nullable = true)
    private int reviewCnt = 0;

    @Column(name = "content", length = 255, nullable = true)
    private String content = "";

    @ManyToOne
    @JoinColumn(name="users_id")
    private Users users;

    @OneToMany(mappedBy = "codes")
    private List<Reviews> reviews;

    @OneToMany(mappedBy = "codes")
    private List<CodeSelectedTags> tags;
}

package com.cody.roughcode.project.entity;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.user.entity.Users;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "code_favorites")
public class CodeFavorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long favoriteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "codes_id", referencedColumnName = "codes_id", insertable = false, updatable = false),
            @JoinColumn(name = "version", referencedColumnName = "version", insertable = false, updatable = false)
    })
    private Codes code;

    @ManyToOne
    @JoinColumn(name="users_id")
    private Users users;

    @Column(name = "content", length = 255, nullable = true)
    private String content = "";
}

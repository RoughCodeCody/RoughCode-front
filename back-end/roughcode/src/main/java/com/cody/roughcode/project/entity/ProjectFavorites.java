package com.cody.roughcode.project.entity;

import com.cody.roughcode.user.entity.Users;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_favorites")
public class ProjectFavorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorites_id", nullable = false, columnDefinition = "BIGINT ")
    private Long favoritesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "projects_id", referencedColumnName = "projects_id", insertable = false, updatable = false),
            @JoinColumn(name = "version", referencedColumnName = "version", insertable = false, updatable = false)
    })
    private Projects project;

    @ManyToOne
    @JoinColumn(name="users_id")
    private Users users;

    @Builder.Default
    @Column(name = "content", length = 255, nullable = true)
    private String content = "";
}

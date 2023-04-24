package com.cody.roughcode.user.entity;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.entity.Reviews;
import com.cody.roughcode.project.entity.CodeFavorites;
import com.cody.roughcode.project.entity.Feedbacks;
import com.cody.roughcode.project.entity.ProjectFavorites;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long usersId;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Builder.Default
    @Column(name = "codes_cnt", nullable = true, columnDefinition = "BIGINT UNSIGNED")
    private Long codesCnt = 0L;

    @Builder.Default
    @Column(name = "projects_cnt", nullable = true, columnDefinition = "BIGINT UNSIGNED")
    private Long projectsCnt = 0L;

    @Column(name = "roles")
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public void projectsCntUp(){
        if(this.projectsCnt == null)
            this.projectsCnt = 0L;
        this.projectsCnt += 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return usersId.equals(users.usersId) && Objects.equals(email, users.email) && name.equals(users.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usersId, email, name);
    }
}

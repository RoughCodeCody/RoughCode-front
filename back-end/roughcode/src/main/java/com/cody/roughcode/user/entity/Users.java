package com.cody.roughcode.user.entity;

import com.cody.roughcode.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "users_id", nullable = false, columnDefinition = "BIGINT ")
    private Long usersId;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @Column(name = "roles")
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();
}

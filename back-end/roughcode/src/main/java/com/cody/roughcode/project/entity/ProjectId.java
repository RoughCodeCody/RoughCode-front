package com.cody.roughcode.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Embeddable
public class ProjectId implements Serializable {

    @Column(name = "projects_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Column(name = "version")
    private int version;

    // equals 메서드 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectId)) return false;
        ProjectId that = (ProjectId) o;
        return Objects.equals(projectId, that.projectId) &&
                version == that.version;
    }

    // hashCode 메서드 구현
    @Override
    public int hashCode() {
        return Objects.hash(projectId, version);
    }
}

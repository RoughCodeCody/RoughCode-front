package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.ProjectTags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTagsRepository extends JpaRepository<ProjectTags, Long> {
    ProjectTags findByTagsId(Long id);
}

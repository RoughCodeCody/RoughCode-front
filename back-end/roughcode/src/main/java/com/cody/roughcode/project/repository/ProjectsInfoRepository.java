package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.entity.ProjectsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectsInfoRepository extends JpaRepository<ProjectsInfo, Long> {
    ProjectsInfo findByProjects(Projects project);
}

package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.entity.ProjectsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectsInfoRepository extends JpaRepository<ProjectsInfo, Long> {
    ProjectsInfo findByProjects(Projects project);

    @Modifying
    @Transactional
    @Query("delete from ProjectsInfo p where p.projects IN :projectsList")
    void deleteAllByProjectsList(@Param("projectsList") List<Projects> projectsList);
}

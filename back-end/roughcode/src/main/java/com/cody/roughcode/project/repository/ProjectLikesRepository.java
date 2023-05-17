package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.ProjectLikes;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectLikesRepository extends JpaRepository<ProjectLikes, Long> {
    ProjectLikes findByProjectsAndUsers(Projects project, Users users);

    List<ProjectLikes> findByProjects(Projects projects);

    @Modifying
    @Transactional
    @Query("delete from ProjectLikes pl where pl.projects IN :projectsList")
    void deleteAllByProjectsList(@Param("projectsList") List<Projects> projectsList);
}

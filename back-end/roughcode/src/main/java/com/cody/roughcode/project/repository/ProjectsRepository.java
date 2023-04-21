package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectsRepository extends JpaRepository<Projects, Long> {
    Projects findByProjectsId(Long id);
    @Query(value = "SELECT p.* FROM Projects p WHERE p.num = (SELECT num FROM Projects WHERE projects_id = :projectsId) AND p.version = (SELECT MAX(version) FROM Projects WHERE num = (SELECT num FROM Projects WHERE projects_id = :projectsId))", nativeQuery = true)
    Projects findProjectWithMaxVersionByProjectsId(@Param("projectsId") Long projectsId);
}

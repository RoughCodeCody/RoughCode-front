package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.ProjectFavorites;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectFavoritesRepository extends JpaRepository<ProjectFavorites, Long> {
    ProjectFavorites findByProjectsAndUsers(Projects project, Users user);
    List<Users> findByProjects(Projects original);
}

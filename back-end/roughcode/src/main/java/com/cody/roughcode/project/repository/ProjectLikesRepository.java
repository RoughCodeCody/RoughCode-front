package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.ProjectLikes;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectLikesRepository extends JpaRepository<ProjectLikes, Long> {
    ProjectLikes findByProjectsAndUsers(Projects project, Users users);
}

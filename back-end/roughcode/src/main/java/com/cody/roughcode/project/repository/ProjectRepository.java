package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Projects, Long> {
}

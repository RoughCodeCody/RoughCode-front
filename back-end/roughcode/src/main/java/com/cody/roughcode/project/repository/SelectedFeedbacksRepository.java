package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.Feedbacks;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.entity.SelectedFeedbacks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectedFeedbacksRepository extends JpaRepository<SelectedFeedbacks, Long> {
    SelectedFeedbacks findByFeedbacksAndProjects(Feedbacks feedbacks, Projects projects);
}

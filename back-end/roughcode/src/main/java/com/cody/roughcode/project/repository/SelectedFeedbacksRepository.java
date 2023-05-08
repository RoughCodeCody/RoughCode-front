package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.Feedbacks;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.entity.SelectedFeedbacks;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SelectedFeedbacksRepository extends JpaRepository<SelectedFeedbacks, Long> {
    SelectedFeedbacks findByFeedbacksAndProjects(Feedbacks feedbacks, Projects projects);

    @Query("SELECT COUNT(sf) FROM SelectedFeedbacks sf WHERE sf.feedbacks.users = :user")
    int countByUsers(@Param("user") Users user);
}

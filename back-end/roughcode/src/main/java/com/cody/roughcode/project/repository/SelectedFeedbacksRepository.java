package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.Feedbacks;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.entity.SelectedFeedbacks;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SelectedFeedbacksRepository extends JpaRepository<SelectedFeedbacks, Long> {
    SelectedFeedbacks findByFeedbacksAndProjects(Feedbacks feedbacks, Projects projects);

    @Query("SELECT COUNT(sf) FROM SelectedFeedbacks sf WHERE sf.feedbacks.users = :user")
    int countByUsers(@Param("user") Users user);

    @Modifying
    @Transactional
    @Query("delete from SelectedFeedbacks sf where sf.projects IN :projectsList")
    void deleteAllByProjectsList(@Param("projectsList") List<Projects> projectsList);
}

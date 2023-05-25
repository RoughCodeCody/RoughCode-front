package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.Feedbacks;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FeedbacksRepository extends JpaRepository<Feedbacks, Long> {
    Feedbacks findByFeedbacksId(Long id);

    int countByUsers(Users users);

    @Modifying
    @Transactional
    @Query("delete from Feedbacks f where f.projectsInfo.projects IN :projectsList")
    void deleteAllByProjectsList(@Param("projectsList") List<Projects> projectsList);
}

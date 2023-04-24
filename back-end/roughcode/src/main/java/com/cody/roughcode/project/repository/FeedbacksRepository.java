package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.Feedbacks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbacksRepository extends JpaRepository<Feedbacks, Long> {
    Feedbacks findFeedbacksByFeedbacksId(Long id);
}

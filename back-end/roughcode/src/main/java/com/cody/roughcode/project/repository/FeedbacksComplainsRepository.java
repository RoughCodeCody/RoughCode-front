package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.Feedbacks;
import com.cody.roughcode.project.entity.FeedbacksComplains;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbacksComplainsRepository extends JpaRepository<FeedbacksComplains, Long> {
    List<FeedbacksComplains> findByFeedbacks(Feedbacks feedbacks);
    FeedbacksComplains findByFeedbacksAndUsers(Feedbacks feedbacks, Users users);
}

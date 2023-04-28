package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.Feedbacks;
import com.cody.roughcode.project.entity.FeedbacksLikes;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbacksLikesRepository extends JpaRepository<FeedbacksLikes, Long> {
    FeedbacksLikes findByFeedbacksAndUsers(Feedbacks feedbacks, Users users);
}

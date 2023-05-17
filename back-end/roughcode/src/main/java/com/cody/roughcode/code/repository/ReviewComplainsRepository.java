package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.ReviewComplains;
import com.cody.roughcode.code.entity.ReviewLikes;
import com.cody.roughcode.code.entity.Reviews;
import com.cody.roughcode.project.entity.Feedbacks;
import com.cody.roughcode.project.entity.FeedbacksComplains;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewComplainsRepository extends JpaRepository<ReviewComplains, Long> {
    List<ReviewComplains> findByReviews(Reviews reviews);
    ReviewComplains findByReviewsAndUsers(Reviews reviews, Users users);
}

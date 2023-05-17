package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.ReReviewComplains;
import com.cody.roughcode.code.entity.ReReviewLikes;
import com.cody.roughcode.code.entity.ReReviews;
import com.cody.roughcode.project.entity.Feedbacks;
import com.cody.roughcode.project.entity.FeedbacksComplains;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReReviewComplainsRepository extends JpaRepository<ReReviewComplains, Long> {
    List<ReReviewComplains> findByReReviews(ReReviews reReviews);
    ReReviewComplains findByReReviewsAndUsers(ReReviews reReviews, Users users);
}

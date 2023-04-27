package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.ReReviewLikes;
import com.cody.roughcode.code.entity.ReReviews;
import com.cody.roughcode.code.entity.ReviewLikes;
import com.cody.roughcode.code.entity.Reviews;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReReviewLikesRepository extends JpaRepository<ReReviewLikes, Long> {

    ReReviewLikes findByReReviewsAndUsers(ReReviews reReview, Users user);

}

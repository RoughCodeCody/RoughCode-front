package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.ReviewLikes;
import com.cody.roughcode.code.entity.Reviews;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikesRepository extends JpaRepository<ReviewLikes, Long> {

    ReviewLikes findByReviewsAndUsers(Reviews review, Users user);

}

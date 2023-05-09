package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.SelectedReviews;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SelectedReviewsRepository extends JpaRepository<SelectedReviews, Long> {
    @Query("SELECT COUNT(sr) FROM SelectedReviews sr WHERE sr.reviews.users = :user")
    int countByUsers(@Param("user") Users user);
}

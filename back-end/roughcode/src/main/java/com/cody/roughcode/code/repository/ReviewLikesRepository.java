package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.ReviewLikes;
import com.cody.roughcode.code.entity.Reviews;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewLikesRepository extends JpaRepository<ReviewLikes, Long> {

    ReviewLikes findByReviewsAndUsers(Reviews review, Users user);

    @Modifying
    @Transactional
    @Query("DELETE FROM ReviewLikes re WHERE re.reviews IN (SELECT r FROM Reviews r WHERE r.codes.codesId = :codesId)")
    void deleteAllByCodesId(@Param("codesId") Long codesId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ReviewLikes re WHERE re.reviews.reviewsId = :reviewsId")
    void deleteAllByReviewId(@Param("reviewsId") Long reviewsId);
}

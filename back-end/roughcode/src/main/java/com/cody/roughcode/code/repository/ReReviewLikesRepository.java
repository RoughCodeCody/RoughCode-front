package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.ReReviewLikes;
import com.cody.roughcode.code.entity.ReReviews;
import com.cody.roughcode.code.entity.ReviewLikes;
import com.cody.roughcode.code.entity.Reviews;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ReReviewLikesRepository extends JpaRepository<ReReviewLikes, Long> {

    ReReviewLikes findByReReviewsAndUsers(ReReviews reReview, Users user);

    @Modifying
    @Transactional
    @Query("delete from ReReviewLikes r "+
            "where r.reReviews.reReviewsId in "+
            "(select rr.reReviewsId from ReReviews rr where rr.reviews.reviewsId in "+
            "(select rv.reviewsId from Reviews rv where rv.codes.codesId = :codesId))")
    void deleteAllByCodesId(@Param("codesId") Long codesId);
}

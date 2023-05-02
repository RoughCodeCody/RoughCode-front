package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.ReReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ReReviewsRepository extends JpaRepository<ReReviews, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ReReviews re WHERE re.reviews IN (SELECT r FROM Reviews r WHERE r.codes.codesId = :codesId)")
    void deleteAllByCodesId(@Param("codesId") Long codesId);
}

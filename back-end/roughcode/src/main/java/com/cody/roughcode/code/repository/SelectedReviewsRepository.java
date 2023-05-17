package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.entity.SelectedReviews;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SelectedReviewsRepository extends JpaRepository<SelectedReviews, Long> {
    @Query("SELECT COUNT(sr) FROM SelectedReviews sr WHERE sr.reviews.users = :user")
    int countByUsers(@Param("user") Users user);

    @Modifying
    @Transactional
    @Query("DELETE FROM SelectedReviews re WHERE re.reviews IN (SELECT r FROM Reviews r WHERE r.codes IN :codesList)")
    void deleteAllByCodesList(@Param("codesList") List<Codes> codesList);
}

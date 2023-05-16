package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.Reviews;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
    Reviews findByReviewsId(Long id);

    @Query("SELECT r FROM Reviews r WHERE r.reviewsId = :id AND r.codes.expireDate IS null")
    Reviews findByReviewsIdAndCodeExpireDateIsNull(@Param("id") Long id);

    List<Reviews> findByReviewsIdIn(List<Long> ids);

    @Modifying
    @Transactional
    @Query("DELETE FROM Reviews r WHERE r.codes.codesId = :codesId")
    void deleteAllByCodesId(@Param("codesId") Long codesId);

    int countByUsers(Users user);
}

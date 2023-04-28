package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
    Reviews findByReviewsId(Long id);
}

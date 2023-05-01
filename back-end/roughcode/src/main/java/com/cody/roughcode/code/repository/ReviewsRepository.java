package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
    Reviews findByReviewsId(Long id);

    List<Reviews> findByReviewsIdIn(List<Long> ids);
}

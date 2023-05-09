package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.ReReviewReq;
import com.cody.roughcode.code.entity.ReReviews;
import com.cody.roughcode.code.entity.Reviews;
import com.cody.roughcode.code.repository.ReReviewsRepository;
import com.cody.roughcode.code.repository.ReviewsRepository;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReReviewsServiceImpl implements ReReviewsService {

    private final UsersRepository usersRepository;
    private final ReReviewsRepository reReviewsRepository;
    private final ReviewsRepository reviewsRepository;

    @Override
    @Transactional
    public int insertReReview(ReReviewReq req, Long usersId) {
        Users users = usersRepository.findByUsersId(usersId);

        Reviews reviews = reviewsRepository.findByReviewsId(req.getReviewsId());

        ReReviews savedReReview = reReviewsRepository.save(
                ReReviews.builder()
                        .users(users)
                        .reviews(reviews)
                        .content(req.getContent())
                        .build()
        );
        reviews.setReReviews(savedReReview);
        reviewsRepository.save(reviews);

        return reviews.getReReviews().size();
    }
}

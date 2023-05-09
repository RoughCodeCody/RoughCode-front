package com.cody.roughcode.code.service;

import com.cody.roughcode.code.dto.req.ReReviewReq;
import com.cody.roughcode.code.dto.res.ReReviewRes;
import com.cody.roughcode.code.entity.ReReviewLikes;
import com.cody.roughcode.code.entity.ReReviews;
import com.cody.roughcode.code.entity.Reviews;
import com.cody.roughcode.code.repository.ReReviewLikesRepository;
import com.cody.roughcode.code.repository.ReReviewsRepository;
import com.cody.roughcode.code.repository.ReviewsRepository;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReReviewsServiceImpl implements ReReviewsService {

    private final UsersRepository usersRepository;
    private final ReReviewsRepository reReviewsRepository;
    private final ReviewsRepository reviewsRepository;
    private final ReReviewLikesRepository reReviewLikesRepository;


    @Override
    @Transactional
    public int insertReReview(ReReviewReq req, Long usersId) {
        Users users = usersRepository.findByUsersId(usersId);

        Reviews reviews = reviewsRepository.findByReviewsId(req.getReviewsId());
        if(reviews == null) throw new NullPointerException("일치하는 리뷰가 존재하지 않습니다");

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

    @Override
    public List<ReReviewRes> getReReviewList(Long reviewsId, Long usersId) {
        Users user = usersRepository.findByUsersId(usersId);

        Reviews reviews = reviewsRepository.findByReviewsId(reviewsId);
        if(reviews == null) throw new NullPointerException("일치하는 리뷰가 존재하지 않습니다");

        List<ReReviews> reReviewsList = reReviewsRepository.findAllByReviewsId(reviewsId);

        List<ReReviewRes> reReviewResList = new ArrayList<>();
        if(reReviewsList != null)
            for (ReReviews r : reReviewsList) {
                ReReviewLikes reReviewLikes = (user != null)? reReviewLikesRepository.findByReReviewsAndUsers(r, user) : null;
                Boolean reReviewLiked = reReviewLikes != null;
                reReviewResList.add(ReReviewRes.toDto(r, reReviewLiked));
            }

        reReviewResList.sort((r1, r2) -> {
                    if (r1.getUserId().equals(usersId) && !r2.getUserId().equals(usersId)) {
                        return -1;
                    } else if (!r1.getUserId().equals(usersId) && r2.getUserId().equals(usersId)) {
                        return 1;
                    } else if(r1.getModifiedDate() != null && r2.getModifiedDate() != null) {
                        return r2.getModifiedDate().compareTo(r1.getModifiedDate());
                    }
                    return 1;
                });

        return reReviewResList;
    }
}

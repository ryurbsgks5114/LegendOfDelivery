package com.sparta.legendofdelivery.domain.like.service;

import com.sparta.legendofdelivery.domain.like.entity.Like;
import com.sparta.legendofdelivery.domain.like.repository.LikeRepository;
import com.sparta.legendofdelivery.domain.review.entity.Review;
import com.sparta.legendofdelivery.domain.review.repository.ReviewRepository;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.global.exception.BadRequestException;
import com.sparta.legendofdelivery.global.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final ReviewRepository reviewRepository;

    public LikeService(LikeRepository likeRepository, ReviewRepository reviewRepository) {
        this.likeRepository = likeRepository;
        this.reviewRepository = reviewRepository;
    }

    public void addLike(Long reviewId, User user) {
        Review review = findReviewById(reviewId);
        Like checkIsLike = findLikeByReviewIdAndUserId(reviewId, user.getId());
        if (checkIsLike != null) {
            throw new BadRequestException("이미 좋아요를 누른 리뷰입니다.");
        } else {
            Like like = new Like(review, user);
            likeRepository.save(like);
        }
    }

    private Review findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> new NotFoundException("해당 id를 가진 리뷰가 없습니다.")
        );
    }

    private Like findLikeByReviewIdAndUserId(Long reviewId, Long userId) {
        return likeRepository.findLikeByReviewIdAndUserId(reviewId,userId).orElse(null);
    }
}

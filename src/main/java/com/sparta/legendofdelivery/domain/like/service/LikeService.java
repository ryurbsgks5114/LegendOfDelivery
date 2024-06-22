package com.sparta.legendofdelivery.domain.like.service;

import com.sparta.legendofdelivery.domain.like.entity.Like;
import com.sparta.legendofdelivery.domain.like.repository.LikeRepository;
import com.sparta.legendofdelivery.domain.review.entity.Review;
import com.sparta.legendofdelivery.domain.review.repository.ReviewRepository;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
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

    public MessageResponse addLike(Long reviewId, User user) {
        Review review = findReviewById(reviewId);
        if (review.getUser().getId().equals(user.getId())){
            throw new BadRequestException("본인이 작성한 리뷰에는 좋아요를 할 수 없습니다.");
        }
        Like checkIsLike = findLikeByReviewIdAndUserId(reviewId, user.getId());
        if (checkIsLike != null) {
            throw new BadRequestException("이미 좋아요를 누른 리뷰입니다.");
        } else {
            Like like = new Like(review, user);
            likeRepository.save(like);
            return new MessageResponse(200, "좋아요 등록에 성공했습니다.");
        }
    }

    public MessageResponse deleteLike(Long reviewId, User user) {
        findReviewById(reviewId);
        Like checkIslike = findLikeByReviewIdAndUserId(reviewId, user.getId());
        if (checkIslike == null) {
            throw new NotFoundException("해당 리뷰는 좋아요가 등록되어 있지 않습니다.");
        } else {
            likeRepository.delete(checkIslike);
            return new MessageResponse(200, "좋아요 취소를 성공했습니다.");
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

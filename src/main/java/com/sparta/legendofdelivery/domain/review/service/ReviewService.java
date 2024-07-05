package com.sparta.legendofdelivery.domain.review.service;


import com.sparta.legendofdelivery.domain.order.repository.OrderRepository;
import com.sparta.legendofdelivery.domain.review.dto.*;
import com.sparta.legendofdelivery.domain.review.entity.Review;
import com.sparta.legendofdelivery.domain.review.repository.ReviewRepository;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.store.service.StoreService;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.domain.user.service.UserService;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import com.sparta.legendofdelivery.global.exception.BadRequestException;
import com.sparta.legendofdelivery.global.exception.NotFoundException;
import com.sparta.legendofdelivery.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sparta.legendofdelivery.domain.review.entity.ErrorCode.*;
import static com.sparta.legendofdelivery.domain.review.entity.successMessage.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final OrderRepository orderRepository;

  private final StoreService storeService;
  private final UserService userService;

  private final PasswordEncoder passwordEncoder;

  @Transactional
  public DataResponse<CreateReviewResponseDto> createReview(CreateReviewRequestDto requestDto) {

    Store store = storeService.findStoreById(requestDto.getStoreId());
    User user = userService.getUser();

    int orderCount = orderRepository.countByUserAndStore(user, store);
    int reviewCount = reviewRepository.countByUserAndStore(user, store);

    if (orderCount <= reviewCount) {
      throw new BadRequestException(REVIEW_CREATION_LIMIT_EXCEEDED.getMessage());
    }

    Review review = reviewRepository.save(new Review(requestDto, store, user));

    return new DataResponse<>(
        REVIEW_CREATED.getStatus(),
        REVIEW_CREATED.getMessage(),
        new CreateReviewResponseDto(review
        ));

  }

  @Transactional(readOnly = true)
  public DataResponse<StoreByReviewResponseDto> getStoreReviewList(Long storeId) {
    Store store = storeService.findStoreById(storeId);
    User user = userService.getUser();
    List<Review> reviewList = reviewRepository.findByUserAndStore(user, store);
    if (null == reviewList) {
      throw new NotFoundException(STORE_REVIEW_NOT_FOUND.getMessage());
    }
    return new DataResponse<>(
        STORE_REVIEWS_FETCHED.getStatus(),
        STORE_REVIEWS_FETCHED.getMessage(),
        new StoreByReviewResponseDto(storeId, user.getUserId(), reviewList
        ));
  }

  @Transactional(readOnly = true)
  public DataResponse<UserReviewResponseDto> getUserReviewList() {
    User user = userService.getUser();
    List<Review> reviewList = reviewRepository.findByUser(user);
    if (null == reviewList) {
      throw new NotFoundException(REVIEW_NOT_FOUND.getMessage());
    }

    return new DataResponse<>(
        USER_REVIEWS_FETCHED.getStatus(),
        USER_REVIEWS_FETCHED.getMessage(),
        new UserReviewResponseDto(user.getUserId(), reviewList
        ));
  }

  @Transactional
  public MessageResponse deleteReview(Long reviewId, DeleteReviewRequestDto requestDto) {
    Review review = findByReviewId(reviewId);
    User user = userService.getUser();
    validatePassword(requestDto.getPassword(),user.getPassword());

    reviewRepository.delete(review);
    return new MessageResponse(REVIEW_DELETION_SUCCESS.getStatus(), REVIEW_DELETION_SUCCESS.getMessage());
  }

  @Transactional
  public MessageResponse updateReview(Long reviewId, UpdateReviewRequestDto requestDto) {
    Store store = storeService.findStoreById(requestDto.getStoreId());
    Review review = findByReviewId(reviewId);
    User user = userService.getUser();
    validatePassword(requestDto.getPassword(), user.getPassword());
    review.updateReview(requestDto, store, user);

    return new MessageResponse(REVIEW_UPDATE_SUCCESS.getStatus(), REVIEW_UPDATE_SUCCESS.getMessage());
  }


  public Review findByReviewId(Long reviewId) {
    return reviewRepository.findById(reviewId)
        .orElseThrow(() -> new NotFoundException(SPECIFIED_REVIEW_NOT_FOUND.getMessage()));
  }

  public void validatePassword(String inputPassword, String dbPassword) {
    if (!passwordEncoder.matches(inputPassword, dbPassword)) {
      throw new UnauthorizedException(DELETE_REVIEW_PERMISSION_DENIED.getMessage());
    }
  }

  public ReviewLikeResponseDto getReview(Long reviewId) {

    Review review = findByReviewId(reviewId);

    ReviewLikeResponseDto responseDto = ReviewLikeResponseDto.builder()
            .content(review.getContent())
            .likeCount(reviewRepository.countLikeByReviewId(reviewId))
            .createAt(review.getCreateAt())
            .modifiedAt(review.getModifiedAt())
            .build();

    return responseDto;
  }

}

package com.sparta.legendofdelivery.domain.review.controller;


import static com.sparta.legendofdelivery.domain.review.entity.successMessage.*;

import com.sparta.legendofdelivery.domain.review.dto.CreateReviewRequestDto;
import com.sparta.legendofdelivery.domain.review.dto.CreateReviewResponseDto;
import com.sparta.legendofdelivery.domain.review.dto.StoreByReviewResponseDto;
import com.sparta.legendofdelivery.domain.review.dto.UserReviewResponseDto;

import com.sparta.legendofdelivery.domain.review.service.ReviewService;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reviews")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping
  public ResponseEntity<DataResponse<CreateReviewResponseDto>> createReview(
      @Valid @RequestBody CreateReviewRequestDto requestDto) {
    CreateReviewResponseDto responseDto = reviewService.createReview(requestDto);
    return ResponseEntity.ok().body(new DataResponse<>(REVIEW_CREATED.getStatus(), REVIEW_CREATED.getMessage(), responseDto)
    );
  }

  @GetMapping("/{storeId}")
  public ResponseEntity<DataResponse<StoreByReviewResponseDto>> storeReviewList(
      @PathVariable Long storeId) {
    StoreByReviewResponseDto responseDtoList = reviewService.storeReviewList(storeId);
    return ResponseEntity.ok()
        .body(new DataResponse<>(STORE_REVIEWS_FETCHED.getStatus(), STORE_REVIEWS_FETCHED.getMessage(), responseDtoList));
  }

  @GetMapping
  public ResponseEntity<DataResponse<UserReviewResponseDto>> getReviewList() {
    UserReviewResponseDto responseDtoList = reviewService.userReviewList();
    return ResponseEntity.ok()
        .body(new DataResponse<>(USER_REVIEWS_FETCHED.getStatus(), USER_REVIEWS_FETCHED.getMessage(), responseDtoList));
  }
}

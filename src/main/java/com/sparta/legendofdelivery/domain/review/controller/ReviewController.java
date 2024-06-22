package com.sparta.legendofdelivery.domain.review.controller;


import com.sparta.legendofdelivery.domain.review.dto.CreateReviewRequestDto;
import com.sparta.legendofdelivery.domain.review.dto.CreateReviewResponseDto;
import com.sparta.legendofdelivery.domain.review.dto.DeleteReviewRequestDto;
import com.sparta.legendofdelivery.domain.review.dto.StoreByReviewResponseDto;
import com.sparta.legendofdelivery.domain.review.dto.UserReviewResponseDto;
import com.sparta.legendofdelivery.domain.review.service.ReviewService;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    return ResponseEntity.ok(reviewService.createReview(requestDto));
  }

  @GetMapping("/{storeId}")
  public ResponseEntity<DataResponse<StoreByReviewResponseDto>> storeReviewList(
      @PathVariable Long storeId) {
    return ResponseEntity.ok(reviewService.storeReviewList(storeId));
  }

  @GetMapping
  public ResponseEntity<DataResponse<UserReviewResponseDto>> getReviewList() {
    return ResponseEntity.ok(reviewService.userReviewList());
  }

  @DeleteMapping("/{reviewId}")
  public ResponseEntity<MessageResponse> deleteReview(@PathVariable Long reviewId,
      @RequestBody DeleteReviewRequestDto requestDto) {
    return ResponseEntity.ok(reviewService.deleteReviewList(reviewId, requestDto));
  }
}

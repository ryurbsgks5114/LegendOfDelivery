package com.sparta.legendofdelivery.domain.review.controller;


import com.sparta.legendofdelivery.domain.review.dto.*;
import com.sparta.legendofdelivery.domain.review.service.ReviewService;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping
  public ResponseEntity<DataResponse<CreateReviewResponseDto>> createReview(@Valid @RequestBody CreateReviewRequestDto requestDto) {

    return ResponseEntity.ok(reviewService.createReview(requestDto));

  }

  @GetMapping("/stores/{storeId}")
  public ResponseEntity<DataResponse<StoreByReviewResponseDto>> getStoreReviewList(@PathVariable Long storeId) {

    return ResponseEntity.ok(reviewService.getStoreReviewList(storeId));

  }

  @GetMapping("/my")
  public ResponseEntity<DataResponse<UserReviewResponseDto>> getUserReviewList() {

    return ResponseEntity.ok(reviewService.getUserReviewList());

  }

  @DeleteMapping("/{reviewId}")
  public ResponseEntity<MessageResponse> deleteReview(@PathVariable Long reviewId,
                                                      @RequestBody DeleteReviewRequestDto requestDto) {

    return ResponseEntity.ok(reviewService.deleteReview(reviewId, requestDto));

  }

  @PutMapping("/{reviewId}")
  public ResponseEntity<MessageResponse> updateReview(@PathVariable Long reviewId,
                                                      @RequestBody UpdateReviewRequestDto requestDto){

    return ResponseEntity.ok(reviewService.updateReview(reviewId, requestDto));

  }

}

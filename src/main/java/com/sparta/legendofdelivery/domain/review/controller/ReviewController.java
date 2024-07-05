package com.sparta.legendofdelivery.domain.review.controller;


import com.sparta.legendofdelivery.domain.review.dto.*;
import com.sparta.legendofdelivery.domain.review.service.ReviewService;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

  @GetMapping("/{reviewId}")
  public ResponseEntity<DataResponse<ReviewLikeResponseDto>> getReview(@PathVariable Long reviewId) {

    ReviewLikeResponseDto responseDto =  reviewService.getReview(reviewId);
    DataResponse<ReviewLikeResponseDto> response = new DataResponse<>(200, "리뷰 조회에 성공했습니다.", responseDto);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

}

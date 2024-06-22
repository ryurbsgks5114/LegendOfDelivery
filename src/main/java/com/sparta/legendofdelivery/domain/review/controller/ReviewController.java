package com.sparta.legendofdelivery.domain.review.controller;


import com.sparta.legendofdelivery.domain.review.dto.ReviewRequestDto;
import com.sparta.legendofdelivery.domain.review.dto.ReviewResponseDto;
import com.sparta.legendofdelivery.domain.review.dto.StoreByReviewResponseDto;
import com.sparta.legendofdelivery.domain.review.service.ReviewService;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import com.sparta.legendofdelivery.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
  public ResponseEntity<DataResponse<ReviewResponseDto>> createReview(
      @Valid @RequestBody ReviewRequestDto requestDto
      , @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    ReviewResponseDto responseDto = reviewService.createReview(requestDto, userDetails.getUsername());

    return ResponseEntity.ok().body(new DataResponse<>(201, "리뷰 생성에 성공했습니다.", responseDto)
    );
  }

  @GetMapping("/{storeId}")
  public ResponseEntity<DataResponse<StoreByReviewResponseDto>> storeReviewList(
      @PathVariable Long storeId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    StoreByReviewResponseDto responseDtoList = reviewService.storeReviewList(storeId, userDetails.getUsername());
    return ResponseEntity.ok().body(new DataResponse<>(200, "가게 별 리뷰 목록 조회에 성공했습니다", responseDtoList));
  }
}

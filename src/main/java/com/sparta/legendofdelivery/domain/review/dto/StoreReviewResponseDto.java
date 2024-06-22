package com.sparta.legendofdelivery.domain.review.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class StoreReviewResponseDto {

  private final Long reviewId;
  private final String content;
  private final LocalDateTime createAt;
  private final LocalDateTime modifiedAt;

  public StoreReviewResponseDto(Long reviewId, String content, LocalDateTime createAt,
      LocalDateTime modifiedAt) {
    this.reviewId = reviewId;
    this.content = content;
    this.createAt = createAt;
    this.modifiedAt = modifiedAt;
  }
}

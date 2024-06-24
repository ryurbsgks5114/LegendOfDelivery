package com.sparta.legendofdelivery.domain.review.dto;

import com.sparta.legendofdelivery.domain.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateReviewResponseDto {

  private final Long reviewId;
  private final Long storeId;
  private final Long userId;
  private final String content;
  private final LocalDateTime createAt;
  private final LocalDateTime modifiedAt;

  public CreateReviewResponseDto(Review review) {
    this.reviewId = review.getId();
    this.storeId = review.getStore().getId();
    this.userId = review.getUser().getId();
    this.content = review.getContent();
    this.createAt = review.getCreateAt();
    this.modifiedAt = review.getModifiedAt();
  }

}

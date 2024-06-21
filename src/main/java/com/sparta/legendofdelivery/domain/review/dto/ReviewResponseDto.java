package com.sparta.legendofdelivery.domain.review.dto;

import com.sparta.legendofdelivery.domain.review.entity.Review;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReviewResponseDto {

  private final Long id;
  private final Long storeId;
  private final Long userId;
  private final String content;
  private final LocalDateTime createAt;
  private final LocalDateTime modifiedAt;

  public ReviewResponseDto(Review review) {
    this.id = review.getId();
    this.storeId = review.getStore().getId();
    this.userId = review.getUser().getId();
    this.content = review.getContent();
    this.createAt = review.getCreateAt();
    this.modifiedAt = review.getModifiedAt();
  }
}

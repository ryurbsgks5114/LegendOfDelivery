package com.sparta.legendofdelivery.domain.review.dto;


import com.sparta.legendofdelivery.domain.review.entity.Review;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class StoreByReviewResponseDto {
  private final Long storeId;
  private final String userId;
  private final List<StoreReviewResponseDto> responseDtoList;


  public StoreByReviewResponseDto(Long storeId, String userId, List<Review> reviews) {
    this.storeId = storeId;
    this.userId = userId;
    this.responseDtoList = reviews.stream()
                    .map(review -> new StoreReviewResponseDto(
                            review.getId(),
                            review.getContent(),
                            review.getCreateAt(),
                            review.getModifiedAt()
                        )
                    )
                    .collect(Collectors.toList());
      }
}

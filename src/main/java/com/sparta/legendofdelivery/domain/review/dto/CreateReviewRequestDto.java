package com.sparta.legendofdelivery.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateReviewRequestDto {

  @NotNull
  private Long storeId;
  private String comment;

}

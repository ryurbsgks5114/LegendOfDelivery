package com.sparta.legendofdelivery.domain.review.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReviewRequestDto {

  @NotBlank
  private Long storeId;
  private String comment;
}

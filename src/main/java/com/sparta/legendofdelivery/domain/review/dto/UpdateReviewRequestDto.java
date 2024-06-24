package com.sparta.legendofdelivery.domain.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateReviewRequestDto {

  @NotNull
  private Long storeId;

  private String content;

  @NotBlank
  private String password;

}

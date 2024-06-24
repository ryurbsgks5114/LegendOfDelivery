package com.sparta.legendofdelivery.domain.review.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteReviewRequestDto {

  @NotBlank
  private String password;

}

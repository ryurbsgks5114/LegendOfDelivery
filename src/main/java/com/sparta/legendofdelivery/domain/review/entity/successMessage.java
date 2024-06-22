package com.sparta.legendofdelivery.domain.review.entity;

import lombok.Getter;

@Getter
public enum successMessage {
  REVIEW_CREATED(201, "리뷰 생성에 성공했습니다."),
  STORE_REVIEWS_FETCHED(200, "가게 별 리뷰 목록 조회에 성공했습니다."),
  USER_REVIEWS_FETCHED(200, "사용자 별 리뷰 목록 조회에 성공했습니다.");

  private final int status;
  private final String message;

  successMessage(int status, String message) {
    this.status = status;
    this.message = message;
  }
}

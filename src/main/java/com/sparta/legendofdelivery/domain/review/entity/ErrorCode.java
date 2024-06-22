package com.sparta.legendofdelivery.domain.review.entity;

import lombok.Getter;

@Getter
public enum ErrorCode {
  REVIEW_CREATION_LIMIT_EXCEEDED("더 이상 리뷰 작성이 안됩니다."),
  REVIEW_NOT_FOUND("작성한 리뷰가 없습니다."),
  STORE_REVIEW_NOT_FOUND("해당 가게의 리뷰를 찾을 수 없습니다."),
  DELETE_REVIEW_PERMISSION_DENIED("삭제할 권한이 없습니다."),
  SPECIFIED_REVIEW_NOT_FOUND("지정한 리뷰를 찾을 수 없습니다.");

  private final String message;

  ErrorCode(String message) {
    this.message = message;
  }
}

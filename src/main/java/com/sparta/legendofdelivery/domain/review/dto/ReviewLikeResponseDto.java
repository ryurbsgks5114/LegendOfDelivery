package com.sparta.legendofdelivery.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewLikeResponseDto {

    private final String content;
    private final Long likeCount;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    @Builder
    public ReviewLikeResponseDto(String content, Long likeCount, LocalDateTime createAt, LocalDateTime modifiedAt) {
        this.content = content;
        this.likeCount = likeCount;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }

}

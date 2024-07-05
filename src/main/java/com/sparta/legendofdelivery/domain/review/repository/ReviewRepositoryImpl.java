package com.sparta.legendofdelivery.domain.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.legendofdelivery.domain.like.entity.QLike;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public long countLikeByReviewId(Long reviewId) {

        QLike qLike = QLike.like;

        return queryFactory
                .select(qLike.count())
                .from(qLike)
                .where(qLike.review.id.eq(reviewId))
                .fetchOne();
    }

}

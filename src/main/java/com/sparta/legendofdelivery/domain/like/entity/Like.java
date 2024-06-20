package com.sparta.legendofdelivery.domain.like.entity;

import com.sparta.legendofdelivery.domain.review.entity.Review;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Like extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public Like(Review review, User user) {
        this.user = user;
        this.review = review;
    }
}

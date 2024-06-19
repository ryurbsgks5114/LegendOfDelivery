package com.sparta.legendofdelivery.domain.review.repository;

import com.sparta.legendofdelivery.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {

}

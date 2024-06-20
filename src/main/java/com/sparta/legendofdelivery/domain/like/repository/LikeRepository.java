package com.sparta.legendofdelivery.domain.like.repository;

import com.sparta.legendofdelivery.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {
}

package com.sparta.legendofdelivery.domain.review.repository;

import com.sparta.legendofdelivery.domain.review.entity.Review;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

  List<Review> findByUserAndStore(User user, Store store);

  int countByUserAndStore(User user, Store store);

  List<Review> findByUser(User user);
}

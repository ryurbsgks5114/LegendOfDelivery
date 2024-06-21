package com.sparta.legendofdelivery.domain.review.service;


import com.sparta.legendofdelivery.domain.order.repository.OrderRepository;
import com.sparta.legendofdelivery.domain.review.dto.ReviewRequestDto;
import com.sparta.legendofdelivery.domain.review.dto.ReviewResponseDto;
import com.sparta.legendofdelivery.domain.review.repository.ReviewRepository;
import com.sparta.legendofdelivery.domain.store.repository.StoreRepository;
import com.sparta.legendofdelivery.domain.store.service.StoreService;
import com.sparta.legendofdelivery.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final StoreService storeService;
  private final OrderRepository orderRepository;

  public ReviewResponseDto createReview(ReviewRequestDto requestDto
//      , User user
  ) {
// Store store = storeService.findById(requestDto.getStoreId();
//    int orderCount = orderRepository.countByUserAndStore(user,store);
//    int reviewCount = reviewRepository.countByUserAndStore(user,store);
//    if(orderCount <= reviewCount) {
//    익셉션
//    }

//  Review review = reviewRepository.save(new Review(requestDto, store, user));
//    return new ReviewResponseDto(review);
    return null;
  }

}

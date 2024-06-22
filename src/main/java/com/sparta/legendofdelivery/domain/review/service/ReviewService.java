package com.sparta.legendofdelivery.domain.review.service;


import com.sparta.legendofdelivery.domain.order.repository.OrderRepository;
import com.sparta.legendofdelivery.domain.review.dto.ReviewRequestDto;
import com.sparta.legendofdelivery.domain.review.dto.ReviewResponseDto;
import com.sparta.legendofdelivery.domain.review.dto.StoreByReviewResponseDto;
import com.sparta.legendofdelivery.domain.review.entity.Review;
import com.sparta.legendofdelivery.domain.review.repository.ReviewRepository;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.store.service.StoreService;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.domain.user.service.UserService;
import com.sparta.legendofdelivery.global.exception.BadRequestException;
import com.sparta.legendofdelivery.global.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final OrderRepository orderRepository;

  private final StoreService storeService;
  private final UserService userService;


  @Transactional
  public ReviewResponseDto createReview(ReviewRequestDto requestDto, String userId) {
    Store store = storeService.findStoreById(requestDto.getStoreId());
    User user = userService.findByUserId(userId).orElseThrow(
        () -> new NotFoundException("존재하지 않는 회원입니다.")
    );

    int orderCount = orderRepository.countByUserAndStore(user, store);
    int reviewCount = reviewRepository.countByUserAndStore(user, store);
    if (orderCount <= reviewCount) {
      throw new BadRequestException("더 이상 리뷰 작성이 안됩니다.");
    }

    Review review = reviewRepository.save(new Review(requestDto, store, user));
    return new ReviewResponseDto(review);
  }

  @Transactional
  public StoreByReviewResponseDto storeReviewList(Long storeId, String userId) {
    Store store = storeService.findStoreById(storeId);
    User user = userService.findByUserId(userId).orElseThrow(
        () -> new NotFoundException("존재하지 않는 회원입니다.")
    );
    List<Review> ReviewList = reviewRepository.findByUserAndStore(user, store);
    return new StoreByReviewResponseDto(storeId, userId, ReviewList);

  }
}

package com.sparta.legendofdelivery.domain.review.service;


import com.sparta.legendofdelivery.domain.order.repository.OrderRepository;
import com.sparta.legendofdelivery.domain.review.dto.CreateReviewRequestDto;
import com.sparta.legendofdelivery.domain.review.dto.CreateReviewResponseDto;
import com.sparta.legendofdelivery.domain.review.dto.StoreByReviewResponseDto;
import com.sparta.legendofdelivery.domain.review.dto.UserReviewResponseDto;
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
  public CreateReviewResponseDto createReview(CreateReviewRequestDto requestDto) {
    Store store = storeService.findStoreById(requestDto.getStoreId());
    User user = userService.getUser();

    int orderCount = orderRepository.countByUserAndStore(user, store);
    int reviewCount = reviewRepository.countByUserAndStore(user, store);
    if (orderCount <= reviewCount) {
      throw new BadRequestException("더 이상 리뷰 작성이 안됩니다.");
    }

    Review review = reviewRepository.save(new Review(requestDto, store, user));
    return new CreateReviewResponseDto(review);
  }

  @Transactional(readOnly = true)
  public StoreByReviewResponseDto storeReviewList(Long storeId) {
    Store store = storeService.findStoreById(storeId);
    User user = userService.getUser();
    List<Review> reviewList = reviewRepository.findByUserAndStore(user, store);
    if(null == reviewList) {
      throw new NotFoundException("해당 가게의 리뷰를 찾을 수 없습니다.");
    }
    return new StoreByReviewResponseDto(storeId, user.getUserId(), reviewList);
  }

  @Transactional(readOnly = true)
  public UserReviewResponseDto userReviewList() {
    User user = userService.getUser();
    List<Review> reviewList = reviewRepository.findByUser(user);
    if(null == reviewList) {
      throw new NotFoundException("작성한 리뷰가 없습니다.");
    }
    return new UserReviewResponseDto(user.getUserId(), reviewList);
  }
}

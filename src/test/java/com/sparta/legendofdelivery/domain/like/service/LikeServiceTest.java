package com.sparta.legendofdelivery.domain.like.service;

import com.sparta.legendofdelivery.domain.like.repository.LikeRepository;
import com.sparta.legendofdelivery.domain.review.entity.Review;
import com.sparta.legendofdelivery.domain.review.repository.ReviewRepository;
import com.sparta.legendofdelivery.domain.review.util.TestUtil;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.domain.user.entity.UserOauth;
import com.sparta.legendofdelivery.domain.user.entity.UserRole;
import com.sparta.legendofdelivery.domain.user.entity.UserStatus;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private LikeService service;

    User user = new User();
    User user2 = new User();
    Store store = new Store();
    Review review = new Review();

    @BeforeEach
    void setUp() throws Exception {
        TestUtil.setField(user, "id", 1L);
        TestUtil.setField(user, "userId", "test1");
        TestUtil.setField(user, "password", "test1234!");
        TestUtil.setField(user, "name", "이름1");
        TestUtil.setField(user, "role", UserRole.USER);
        TestUtil.setField(user, "status", UserStatus.NORMAL);
        TestUtil.setField(user, "oauth", UserOauth.OUR);

        TestUtil.setField(user2, "id", 2L);
        TestUtil.setField(user2, "userId", "test2");
        TestUtil.setField(user2, "password", "test1234!");
        TestUtil.setField(user2, "name", "이름1");
        TestUtil.setField(user2, "role", UserRole.USER);
        TestUtil.setField(user2, "status", UserStatus.NORMAL);
        TestUtil.setField(user2, "oauth", UserOauth.OUR);

        TestUtil.setField(review, "id", 1L);
        TestUtil.setField(review, "content", "");
        TestUtil.setField(review, "user", user);
        TestUtil.setField(review, "store", store);

        when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));

        when(likeRepository.findLikeByReviewIdAndUserId(review.getId(), user2.getId())).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("좋아요 등록 성공")
    void addDibsSuccess() throws Exception {

        // Call the actual service method
        MessageResponse response = service.addLike(review.getId(), user2);

        // Assertions remain the same
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("좋아요 등록에 성공했습니다.", response.getMessage());
    }
}
package com.sparta.legendofdelivery.domain.order.service;

import com.sparta.legendofdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.legendofdelivery.domain.order.dto.OrderResponseDto;
import com.sparta.legendofdelivery.domain.order.entity.Order;
import com.sparta.legendofdelivery.domain.order.entity.OrderStatusEnum;
import com.sparta.legendofdelivery.domain.order.repository.OrderRepository;
import com.sparta.legendofdelivery.domain.review.util.TestUtil;
import com.sparta.legendofdelivery.domain.store.entity.Category;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.store.repository.StoreRepository;
import com.sparta.legendofdelivery.domain.store.service.StoreService;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.domain.user.entity.UserOauth;
import com.sparta.legendofdelivery.domain.user.entity.UserRole;
import com.sparta.legendofdelivery.domain.user.entity.UserStatus;
import com.sparta.legendofdelivery.domain.user.service.UserService;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import com.sparta.legendofdelivery.global.exception.NotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private StoreService storeService;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrderService orderService;

    private User user;
    private Store store;
    private Order order;

    @BeforeEach
    void setUp() throws Exception {
        user = new User();
        store = new Store();
        order = new Order();

        TestUtil.setField(user, "id", 1L);
        TestUtil.setField(user, "userId", "test1234");
        TestUtil.setField(user, "password", "test1234!");
        TestUtil.setField(user, "name", "이름");
        TestUtil.setField(user, "role", UserRole.USER);
        TestUtil.setField(user, "status", UserStatus.NORMAL);
        TestUtil.setField(user, "oauth", UserOauth.OUR);

        TestUtil.setField(store, "id", 1L);
        TestUtil.setField(store, "category", Category.KOREAN);

        TestUtil.setField(order, "id", 1L);
        TestUtil.setField(order, "user", user);
        TestUtil.setField(order, "store", store);
        TestUtil.setField(order, "orderStatus", OrderStatusEnum.ACCEPTANCE);
        TestUtil.setField(order, "count", 1);
        TestUtil.setField(order, "totalPrice", 1000L);
    }

    @Test
    @DisplayName("주문 생성 성공")
    void createOrder_success() throws Exception {
        OrderRequestDto requestDto = new OrderRequestDto();
        TestUtil.setField(requestDto, "storeId", 1L);
        TestUtil.setField(requestDto, "count", 2); // Add appropriate items

        when(storeRepository.findById(requestDto.getStoreId())).thenReturn(Optional.of(store));
        when(userService.getUser()).thenReturn(user);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        DataResponse<OrderResponseDto> response = orderService.createOrder(requestDto);


        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("주문 생성에 성공했습니다.", response.getMessage());
        assertNotNull(response.getData());
    }

    @Test
    @DisplayName("주문 생성 실패 - 가게 없음")
    void createOrder_fail_storeNotFound() throws Exception {
        // 가게 ID가 유효하지 않을 때를 시뮬레이션하는 코드
        OrderRequestDto requestDto = new OrderRequestDto();
        TestUtil.setField(requestDto, "storeId", 999L); // 존재하지 않는 가게 ID 설정
        TestUtil.setField(requestDto, "count", 2);

        // 사용자 Mock 설정
        when(userService.getUser()).thenReturn(user);

        // 주문 생성 시 가게를 찾을 수 없어 NotFoundException 발생 예상
        assertThrows(NotFoundException.class, () -> orderService.createOrder(requestDto));

        // Verify 저장 메서드 호출하지 않음
        verify(orderRepository, never()).save(any(Order.class));
    }
}
package com.sparta.legendofdelivery.domain.order.service;

import com.sparta.legendofdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.legendofdelivery.domain.order.dto.OrderResponseDto;
import com.sparta.legendofdelivery.domain.order.entity.Order;
import com.sparta.legendofdelivery.domain.order.entity.OrderStatusEnum;
import com.sparta.legendofdelivery.domain.order.repository.OrderRepository;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.store.repository.StoreRepository;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.domain.user.repository.UserRepository;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, StoreRepository storeRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
    }

    public DataResponse<OrderResponseDto> createOrder(Long storeId, Long userId, OrderRequestDto requestDto) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("유저를 찾을 수 없습니다.")
        );

        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new IllegalArgumentException("가게를 찾을 수 없습니다")
        );

        Order order = new Order(requestDto, user, store);
        Order saveOrder = orderRepository.save(order);

        return new DataResponse<>(200, "주문 생성에 성공했습니다.", new OrderResponseDto(saveOrder));
    }

    public DataResponse<List<OrderResponseDto>> getAllOrderByClient(Long userId, int page, int size, String sortBy) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("유저를 찾을 수 없습니다.")
        );

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<OrderResponseDto> OrderPage = orderRepository.findAllByUser(user, pageable).map(OrderResponseDto::new);
        List<OrderResponseDto> responseDtoList = OrderPage.getContent();

        if (responseDtoList.isEmpty()) {
            throw new IllegalArgumentException("생성된 주문이 없습니다.");
        }

        return new DataResponse<>(200, "주문 전체 목록 조회에 성공했습니다.", responseDtoList);
    }

    // status 상태 변경
    public DataResponse<OrderResponseDto> updateOrderStatus(Long orderId, OrderStatusEnum updateStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("상태를 변경하려는 주문을 찾을 수 없습니다."));
        order.updateOrderStatus(updateStatus);
        Order saveOrder = orderRepository.save(order);

        OrderResponseDto responseDto = new OrderResponseDto(saveOrder);
        return new DataResponse<>(200, "주문 상태 변경에 성공했습니다.", responseDto);
    }

}
